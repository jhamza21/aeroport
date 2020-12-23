import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAeroport } from 'app/shared/model/aeroport.model';

type EntityResponseType = HttpResponse<IAeroport>;
type EntityArrayResponseType = HttpResponse<IAeroport[]>;

@Injectable({ providedIn: 'root' })
export class AeroportService {
  public resourceUrl = SERVER_API_URL + 'api/aeroports';

  constructor(protected http: HttpClient) {}

  create(aeroport: IAeroport): Observable<EntityResponseType> {
    return this.http.post<IAeroport>(this.resourceUrl, aeroport, { observe: 'response' });
  }

  update(aeroport: IAeroport): Observable<EntityResponseType> {
    return this.http.put<IAeroport>(this.resourceUrl, aeroport, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAeroport>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAeroport[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
