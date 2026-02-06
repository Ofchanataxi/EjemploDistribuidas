export interface Plan {
  id?: number;
  nombre: string;
  tipo: string; // VIDA, AUTO, SALUD
  primaBase: number;
  coberturaMax: number;
}