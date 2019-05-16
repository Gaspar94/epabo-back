import { Moment } from 'moment';

export interface IGasto {
  id?: number;
  descripcion?: string;
  fecha?: Moment;
  fechaModificacion?: Moment;
  montoGasto?: number;
  userId?: number;
}

export const defaultValue: Readonly<IGasto> = {};
