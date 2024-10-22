import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class HttpService {


  apiUrl=""



  constructor(private http:HttpClient) {

    
   }

   getProjectInformation(){
   return this.http.get(`${this.apiUrl}/project`)

   }




  
}
