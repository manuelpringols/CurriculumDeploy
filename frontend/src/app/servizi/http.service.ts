import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpService {


  apiUrl="https://manuelpringols.info:8080/api/project"

  apiUrlAuth="https://manuelpringols.info:8080/api/auth"



  constructor(private http:HttpClient) {

    
   }

   getProjectDescription(id:number):Observable<String>{
    const token = localStorage.getItem('tokenAuth');

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);


   return this.http.get(`${this.apiUrl}/getDescription/${id} `, { responseType: 'text',headers } )
   }


   getProjectTitle(id:number){
    const token = localStorage.getItem('tokenAuth');

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);


    return this.http.get(`${this.apiUrl}/getTitle/${id}`,{ responseType: 'text',headers })
    }


    register(body:any){

      return this.http.post(`${this.apiUrlAuth}/register`, body,{ responseType: 'text' } )
    }


    authenticate(body:any){
      const token = localStorage.getItem('jwtToken');

      if (!token) {
        throw new Error('Token non trovato. Effettua prima la registrazione.');
      }
    
      // Aggiunge l'intestazione Authorization con il token
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    
      // Invio della richiesta con l'intestazione
      return this.http.post(`${this.apiUrlAuth}/authenticate`, body, { headers, responseType: 'text' });
    }




  
}
