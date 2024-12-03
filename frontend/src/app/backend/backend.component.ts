import { Component, EventEmitter, Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import { HttpService } from '../servizi/http.service';
import { HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-backend',
  templateUrl: './backend.component.html',
  styleUrl: './backend.component.css'
})
export class BackendComponent {
showLoginInfo: boolean = false;
showRegisterInfo: boolean= false

  showCode = false; // Stato per mostrare o nascondere il codice
registerEmail: any;
registerPassword: any;
loginEmail: any;
loginPassword: any;
jwtToken: any;
isAuthenticated: any;
@Output() authStatus = new EventEmitter<boolean>();
@Output() dataProject = new EventEmitter<any>();

  projectInformation1: any;
  projectInformation2: any;


constructor(private http:HttpService){

}


  toggleCode() {
    this.showCode = !this.showCode; // Cambia lo stato di visibilità
  }

  onRegister(registerForm:NgForm) {
    // Simulazione della generazione di un token JWT


    
    this.registerEmail = registerForm.value.email;
    this.registerPassword = registerForm.value.password;

    const body = { email: this.registerEmail, password: this.registerPassword };
    console.log(this.loginEmail,this.loginPassword)

    this.http.register(body).subscribe({next:(token:string)=>{

      localStorage.setItem('jwtToken', token);
      console.log('Token salvato nel localStorage:', token);
      this.jwtToken=token;

    },
    error: (err) => {
      console.error('Errore durante la registrazione:', err);
      // Mostrare una notifica all'utente
    }

    });

    //this.jwtToken = 'token_' + Math.random().toString(36).substring(2, 15); // Genera un token casuale
    //localStorage.setItem('jwtToken', this.jwtToken); // Salva il token nel localStorage
    this.showRegisterInfo = true;

    registerForm.reset()
  }

  getProjectDescription1(){
      this.http.getProjectDescription(1).subscribe((dati: any) => {
        console.log('dati ricevuti', dati);
        this.projectInformation1 = dati
        this.dataProject.emit(dati)
        });
     
     
    }
  
  
    getProjectDescription2(){
      this.http.getProjectDescription(2).subscribe((dati: any) => {
        console.log('dati ricevuti', dati);
        this.projectInformation2 = dati
        this.dataProject.emit(dati)

  
        });
     
     
    }

  // Simula il login
  onLogin(loginForm: NgForm) {

   // if (!registerToken) {
   //   throw new Error('Token non trovato. Effettua prima la registrazione.');
   // }


   
      // Confronta le credenziali
      const emailInput = loginForm.value.email;
      const passwordInput = loginForm.value.password;
      const tokenInput = loginForm.value.jwtToken;


      const loginBody = {
        email: emailInput,
        password: passwordInput,
        
      };

      console.log('Credenziali inserite:', emailInput, passwordInput);
      console.log('tokenRegister salvato:',localStorage.getItem('jwtToken'))

      this.http.authenticate(loginBody).subscribe({
        next: (tokenAuth) => {
          console.log('Accesso riuscito:', tokenAuth);
          this.isAuthenticated = true; // L'utente è autenticato
          this.authStatus.emit(this.isAuthenticated);
          this.showLoginInfo = true;
          this.onRegister
          
          if (tokenAuth) {
            localStorage.setItem('tokenAuth', tokenAuth);
          } else {
            console.error('Token di autenticazione non ricevuto.');
          }
    
          alert('Accesso effettuato con successo!');
        },
        error: (error) => {
          console.error('Errore durante il login:', error);
          alert('Errore durante l\'accesso. Controlla le credenziali.');
        },
      });

      //if (emailInput !== this.registerEmail || passwordInput !== this.registerPassword) {
      //   alert('Le credenziali non sono corrette. Riprova.');
     //  } else {
        // Logica per il login
     //    alert('Accesso riuscito!');
        // Qui potresti salvare il token o reindirizzare l'utente
     //  }






      loginForm.reset()
      // Reset del modulo
    

  //   if (tokenInput === this.jwtToken) {
  //     this.isAuthenticated = true; // L'utente è autenticato
  //     this.authStatus.emit(this.isAuthenticated);
  //     this.showLoginInfo = true;
  //   } else {
  //     alert('Token non valido!'); // Gestione errore
  //   }
  // }
    }


    
}