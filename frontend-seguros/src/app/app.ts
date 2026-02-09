import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ApiService } from './services/api.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './app.html',
  // Si no tienes el archivo app.css, borra la siguiente línea:
  styleUrls: ['./app.css']
})
export class App implements OnInit {

  clientes: any[] = [];
  planes: any[] = [];
  polizas: any[] = [];

  nuevo: any = {
    numeroPoliza: '',
    clienteId: 0,
    planId: 0,
    primaMensual: 0,
    fechaInicio: '',
    fechaFin: '',
    estado: 'ACTIVA'
  };

  nuevoCliente: any = {
    nombres: '',
    identificacion: '',
    email: '',
    telefono: ''
  };

  nuevoPlan: any = {
    nombre: '',
    tipo: 'AUTO',
    primaBase: 0,
    coberturaMax: 0
  };

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.cargarClientes();
    this.cargarPlanes();
    this.cargarPolizas();
  }

  cargarClientes() {
    this.api.getClientes().subscribe({
      next: data => this.clientes = data,
      error: err => console.error('Error cargando clientes', err)
    });
  }

  cargarPlanes() {
    this.api.getPlanes().subscribe({
      next: data => this.planes = data,
      error: err => console.error('Error cargando planes', err)
    });
  }

  cargarPolizas() {
    this.api.getPolizas().subscribe({
      next: data => this.polizas = data,
      error: err => console.error('Error cargando polizas', err)
    });
  }

  guardar() {
    // validación mínima
    if (!this.nuevo.numeroPoliza || !this.nuevo.clienteId || !this.nuevo.planId) {
      alert('Complete número de póliza, cliente y plan.');
      return;
    }
    this.api.createPoliza(this.nuevo).subscribe({
      next: () => {
        alert('Póliza creada');
        this.cargarPolizas();
      },
      error: err => { console.error(err); alert('Error creando póliza'); }
    });
  }

  guardarCliente() {
    if (!this.nuevoCliente.nombres || !this.nuevoCliente.identificacion) {
      alert('Nombre e Identificación son requeridos.');
      return;
    }
    this.api.createCliente(this.nuevoCliente).subscribe({
      next: () => {
        alert('Cliente creado');
        this.cargarClientes();
        // Reset form
        this.nuevoCliente = { nombres: '', identificacion: '', email: '', telefono: '' };
      },
      error: err => { console.error(err); alert('Error creando cliente'); }
    });
  }

  eliminarCliente(id: number) {
    if (!confirm('¿Eliminar cliente id=' + id + '?')) return;
    this.api.deleteCliente(id).subscribe({
      next: () => this.cargarClientes(),
      error: err => { console.error(err); alert('Error eliminando cliente'); }
    });
  }

  guardarPlan() {
    if (!this.nuevoPlan.nombre || !this.nuevoPlan.tipo) {
      alert('Nombre y Tipo son requeridos.');
      return;
    }
    this.api.createPlan(this.nuevoPlan).subscribe({
      next: () => {
        alert('Plan creado');
        this.cargarPlanes();
        // Reset form
        this.nuevoPlan = { nombre: '', tipo: 'AUTO', primaBase: 0, coberturaMax: 0 };
      },
      error: err => { console.error(err); alert('Error creando plan'); }
    });
  }

  eliminarPlan(id: number) {
    if (!confirm('¿Eliminar plan id=' + id + '?')) return;
    this.api.deletePlan(id).subscribe({
      next: () => this.cargarPlanes(),
      error: err => { console.error(err); alert('Error eliminando plan'); }
    });
  }

  eliminar(id: number) {
    if (!confirm('¿Eliminar póliza id=' + id + '?')) return;
    this.api.deletePoliza(id).subscribe({
      next: () => this.cargarPolizas(),
      error: err => { console.error(err); alert('Error eliminando póliza'); }
    });
  }
}
