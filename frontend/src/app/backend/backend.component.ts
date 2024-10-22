import { Component, EventEmitter, Output } from '@angular/core';
import { NgForm } from '@angular/forms';

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


  toggleCode() {
    this.showCode = !this.showCode; // Cambia lo stato di visibilità
  }

  onRegister(registerForm:NgForm) {
    // Simulazione della generazione di un token JWT
    this.registerEmail = registerForm.value.email;
    this.registerPassword = registerForm.value.password;
    console.log(this.loginEmail,this.loginPassword)
    this.jwtToken = 'token_' + Math.random().toString(36).substring(2, 15); // Genera un token casuale
    localStorage.setItem('jwtToken', this.jwtToken); // Salva il token nel localStorage
    this.showRegisterInfo = true;

    registerForm.reset()
  }

  // Simula il login
  onLogin(loginForm: NgForm) {
    const tokenInput = loginForm.value.jwtToken;

   
      // Confronta le credenziali
      const emailInput = loginForm.value.email;
      const passwordInput = loginForm.value.password;

      console.log(emailInput,passwordInput,this.registerEmail,this.registerPassword)

      if (emailInput !== this.registerEmail || passwordInput !== this.registerPassword) {
        alert('Le credenziali non sono corrette. Riprova.');
      } else {
        // Logica per il login
        alert('Accesso riuscito!');
        // Qui potresti salvare il token o reindirizzare l'utente
      }

      loginForm.reset()
      // Reset del modulo
    

    if (tokenInput === this.jwtToken) {
      this.isAuthenticated = true; // L'utente è autenticato
      this.authStatus.emit(this.isAuthenticated);
      this.showLoginInfo = true;
    } else {
      alert('Token non valido!'); // Gestione errore
    }
  }
}