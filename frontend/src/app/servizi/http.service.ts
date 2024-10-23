import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpService {


  apiUrl="http://188.245.185.96:8080/api/project"



  constructor(private http:HttpClient) {

    
   }

   getProjectDescription(id:number):Observable<any>{
   return this.http.get(`${this.apiUrl}/getDescription/${id} ` )
   }


   getProjectTitle(id:number){
    return this.http.get(`${this.apiUrl}/getTitle/${id}`)
    }




  
}
