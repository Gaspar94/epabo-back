import { Moment } from 'moment';

export interface IOportunidad {
  id?: number;
  descripcion?: string;
  fecha?: Moment;
  ubicacion?: string;
  autor?: number;
  email?: string;
  tags?: string;
  urlImagen?: string;
}

export const defaultValue: Readonly<IOportunidad> = {};
