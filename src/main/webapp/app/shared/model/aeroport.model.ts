import { IAvion } from 'app/shared/model/avion.model';

export interface IAeroport {
  id?: number;
  name?: string;
  ville?: string;
  maxAvion?: number;
  avions?: IAvion[];
}

export class Aeroport implements IAeroport {
  constructor(public id?: number, public name?: string, public ville?: string, public maxAvion?: number, public avions?: IAvion[]) {}
}
