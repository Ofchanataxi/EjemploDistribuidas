import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from './services/api.service';
import { Cliente } from './models/cliente.model';
import { Plan } from './models/plan.model';
import { Poliza } from './models/poliza.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css' // Ojo: verifica si es .css o .scss
})
export class AppComponent implements OnInit {
  polizas: Poliza[] = [];
  clientes: Cliente[] = [];
  planes: Plan[] = [];

  nuevo: Poliza = {
    numeroPoliza: '',
    fechaInicio: '',
    fechaFin: '',
    primaMensual: 0,
    estado: 'ACTIVA',
    clienteId: 0,
    planId: 0
  };

  constructor(private api: ApiService) {}

  ngOnInit() {
    this.cargarDatos();
  }

  cargarDatos() {
    this.api.getClientes().subscribe(data => this.clientes = data);
    this.api.getPlanes().subscribe(data => this.planes = data);
    this.api.getPolizas().subscribe(data => this.polizas = data);
  }

  guardar() {
    this.api.createPoliza(this.nuevo).subscribe({
      next: () => {
        alert('Póliza creada correctamente');
        this.cargarDatos();
        this.limpiarFormulario();
      },
      error: (e) => alert('Error: ' + e.message)
    });
  }

  eliminar(id?: number) {
    if(id && confirm('¿Borrar póliza?')) {
      this.api.deletePoliza(id).subscribe(() => this.cargarDatos());
    }
  }

  limpiarFormulario() {
    this.nuevo = {
      numeroPoliza: '',
      fechaInicio: '',
      fechaFin: '',
      primaMensual: 0,
      estado: 'ACTIVA',
      clienteId: 0,
      planId: 0
    };
  }
}