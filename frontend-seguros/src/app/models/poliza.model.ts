export interface Poliza {
  id?: number;
  numeroPoliza: string;
  fechaInicio: string; // Formato YYYY-MM-DD
  fechaFin: string;
  primaMensual: number;
  estado: string; // ACTIVA, CANCELADA
  clienteId: number;
  planId: number;
}