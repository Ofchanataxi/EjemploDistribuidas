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
  // URLs dinámicas según el entorno:
  // - Desarrollo local (ng serve en puerto 4200): apunta a microservicios locales directos
  // - Docker Compose (puerto 8080): usa rutas relativas, nginx hace proxy a los contenedores
  private isDevelopment = window.location.port === '4200';
  
  private clientesUrl = this.isDevelopment ? 'http://localhost:8080/api/clientes' : '/api/clientes';
  private planesUrl = this.isDevelopment ? 'http://localhost:8081/api/planes' : '/api/planes';
  private polizasUrl = this.isDevelopment ? 'http://localhost:8001/api/polizas' : '/api/polizas';

  constructor(private http: HttpClient) { }

  // --- CLIENTES ---
  getClientes(): Observable<Cliente[]> { return this.http.get<Cliente[]>(this.clientesUrl); }
  createCliente(cliente: Cliente): Observable<Cliente> { return this.http.post<Cliente>(this.clientesUrl, cliente); }
  deleteCliente(id: number): Observable<void> { return this.http.delete<void>(`${this.clientesUrl}/${id}`); }

  // --- PLANES ---
  getPlanes(): Observable<Plan[]> { return this.http.get<Plan[]>(this.planesUrl); }
  createPlan(plan: Plan): Observable<Plan> { return this.http.post<Plan>(this.planesUrl, plan); }
  deletePlan(id: number): Observable<void> { return this.http.delete<void>(`${this.planesUrl}/${id}`); }

  // --- POLIZAS ---
  getPolizas(): Observable<Poliza[]> { return this.http.get<Poliza[]>(this.polizasUrl); }
  createPoliza(poliza: Poliza): Observable<Poliza> { return this.http.post<Poliza>(this.polizasUrl, poliza); }
  deletePoliza(id: number): Observable<void> { return this.http.delete<void>(`${this.polizasUrl}/${id}`); }
}
