import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cliente } from '../models/cliente.model';
import { Plan } from '../models/plan.model';
import { Poliza } from '../models/poliza.model';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  // Puertos definidos en tu docker-compose
  private clientesUrl = 'http://localhost:8080/api/clientes';
  private planesUrl = 'http://localhost:8081/api/planes';
  private polizasUrl = 'http://localhost:8001/api/polizas';

  constructor(private http: HttpClient) { }

  // --- CLIENTES ---
  getClientes(): Observable<Cliente[]> { return this.http.get<Cliente[]>(this.clientesUrl); }
  
  // --- PLANES ---
  getPlanes(): Observable<Plan[]> { return this.http.get<Plan[]>(this.planesUrl); }
  
  // --- POLIZAS ---
  getPolizas(): Observable<Poliza[]> { return this.http.get<Poliza[]>(this.polizasUrl); }
  createPoliza(poliza: Poliza): Observable<Poliza> { return this.http.post<Poliza>(this.polizasUrl, poliza); }
  deletePoliza(id: number): Observable<void> { return this.http.delete<void>(`${this.polizasUrl}/${id}`); }
}