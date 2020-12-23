import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAeroport } from 'app/shared/model/aeroport.model';

@Component({
  selector: 'jhi-aeroport-detail',
  templateUrl: './aeroport-detail.component.html',
})
export class AeroportDetailComponent implements OnInit {
  aeroport: IAeroport | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aeroport }) => (this.aeroport = aeroport));
  }

  previousState(): void {
    window.history.back();
  }
}
