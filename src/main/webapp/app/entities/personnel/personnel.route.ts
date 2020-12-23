import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPersonnel, Personnel } from 'app/shared/model/personnel.model';
import { PersonnelService } from './personnel.service';
import { PersonnelComponent } from './personnel.component';
import { PersonnelDetailComponent } from './personnel-detail.component';
import { PersonnelUpdateComponent } from './personnel-update.component';

@Injectable({ providedIn: 'root' })
export class PersonnelResolve implements Resolve<IPersonnel> {
  constructor(private service: PersonnelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPersonnel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((personnel: HttpResponse<Personnel>) => {
          if (personnel.body) {
            return of(personnel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Personnel());
  }
}

export const personnelRoute: Routes = [
  {
    path: '',
    component: PersonnelComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'aeroportApp.personnel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PersonnelDetailComponent,
    resolve: {
      personnel: PersonnelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'aeroportApp.personnel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PersonnelUpdateComponent,
    resolve: {
      personnel: PersonnelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'aeroportApp.personnel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PersonnelUpdateComponent,
    resolve: {
      personnel: PersonnelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'aeroportApp.personnel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
