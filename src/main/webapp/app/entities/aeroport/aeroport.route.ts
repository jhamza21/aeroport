import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAeroport, Aeroport } from 'app/shared/model/aeroport.model';
import { AeroportService } from './aeroport.service';
import { AeroportComponent } from './aeroport.component';
import { AeroportDetailComponent } from './aeroport-detail.component';
import { AeroportUpdateComponent } from './aeroport-update.component';

@Injectable({ providedIn: 'root' })
export class AeroportResolve implements Resolve<IAeroport> {
  constructor(private service: AeroportService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAeroport> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((aeroport: HttpResponse<Aeroport>) => {
          if (aeroport.body) {
            return of(aeroport.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Aeroport());
  }
}

export const aeroportRoute: Routes = [
  {
    path: '',
    component: AeroportComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'aeroportApp.aeroport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AeroportDetailComponent,
    resolve: {
      aeroport: AeroportResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'aeroportApp.aeroport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AeroportUpdateComponent,
    resolve: {
      aeroport: AeroportResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'aeroportApp.aeroport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AeroportUpdateComponent,
    resolve: {
      aeroport: AeroportResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'aeroportApp.aeroport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
