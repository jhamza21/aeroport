import { Moment } from 'moment';
import { IPersonnel } from 'app/shared/model/personnel.model';
import { IAeroport } from 'app/shared/model/aeroport.model';

export interface IAvion {
  id?: number;
  matricule?: string;
  company?: string;
  dateArr?: Moment;
  dateDep?: Moment;
  personnel?: IPersonnel[];
  aeroport?: IAeroport;
}

export class Avion implements IAvion {
  constructor(
    public id?: number,
    public matricule?: string,
    public company?: string,
    public dateArr?: Moment,
    public dateDep?: Moment,
    public personnel?: IPersonnel[],
    public aeroport?: IAeroport
  ) {}
}
