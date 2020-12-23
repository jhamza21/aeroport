import { IAvion } from 'app/shared/model/avion.model';

export interface IPersonnel {
  id?: number;
  lastName?: string;
  firstName?: string;
  nationality?: string;
  avion?: IAvion;
}

export class Personnel implements IPersonnel {
  constructor(
    public id?: number,
    public lastName?: string,
    public firstName?: string,
    public nationality?: string,
    public avion?: IAvion
  ) {}
}
