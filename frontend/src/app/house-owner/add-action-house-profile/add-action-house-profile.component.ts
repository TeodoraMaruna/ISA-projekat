import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HouseReservation} from "../../model/house-reservation";
import {HouseReservationService} from "../../service/house-reservation.service";
import {AlertService} from "ngx-alerts";
import {AdditionalService} from "../../model/additional-service";
import {AdditionalServicesService} from "../../service/additional-services.service";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-add-action-house-profile',
  templateUrl: './add-action-house-profile.component.html',
  styleUrls: ['./add-action-house-profile.component.css']
})
export class AddActionHouseProfileComponent implements OnInit {

  houseId: number = 0;
  additionalServices: AdditionalService[] = new Array();
  additionalServicesFinal: AdditionalService[] = new Array();
  houseReservation: HouseReservation = new HouseReservation(0, '', '', 0, this.additionalServices, 0, true);
  duration: number = 0;
  date: Date = new Date();
  endDate: Date = new Date();
  startDate: string = '';
  min: number = 0;

  // _route: ActivatedRoute - da iz mog url-a preuzmem id
  constructor(private _route: ActivatedRoute, private _houseReservationService: HouseReservationService,
              private _alertService: AlertService, private _router: Router, private _additionalServicesService: AdditionalServicesService,
              public datepipe: DatePipe) { }

  ngOnInit(): void {
    // @ts-ignore
    this.houseId = +this._route.snapshot.paramMap.get('id');

    var date = new Date();
    var miliseconds = date.getTime();
    miliseconds = miliseconds + 3600000*2;
    date = new Date(miliseconds)

    this.startDate = date.toISOString().slice(0, 16);
    this.loadData();
  }

  loadData(){
    this._additionalServicesService.getAllByHouseId(this.houseId).subscribe(
      (additionalServices: AdditionalService[]) => {
        this.additionalServices = additionalServices
      }
    )
  }

  addAction() {
    if (this.duration > 0 && this.houseReservation.maxGuests > 0 && this.houseReservation.price > 0 && this.houseReservation.startDate != '') {
      this.houseReservation.houseId = this.houseId;
      this.houseReservation.action = true;
      this.houseReservation.available = true;
      this.houseReservation.availabilityPeriod = false;
      this.houseReservation.cancelled = false;

      var startDate = Date.parse(this.houseReservation.startDate)
      this.date = new Date(startDate)

      var actionStart = Number(this.date)  // parsiranje datuma pocetka u milisekunde
      var actionEnd = actionStart + this.duration * 86400000

      this.houseReservation.startDate = actionStart.toString()
      this.houseReservation.endDate = actionEnd.toString()

      for (let a of this.additionalServices) {
        if (a.checked == true) {
          this.additionalServicesFinal.push(a)
        }
      }

      this.houseReservation.additionalServices = this.additionalServicesFinal
      console.log(this.houseReservation)

      this._houseReservationService.save(this.houseReservation).subscribe(   // subscribe - da bismo dobili odgovor beka
        (houseReservation: HouseReservation) => {

          this._router.navigate(['house-profile-for-house-owner/', this.houseId])
        },
        (error) => {
          this._alertService.danger('Vec postoji termin u izabranom vremenskom periodu');
        }
      )
    } else {

      if(this.houseReservation.startDate == '')
      {
        this._alertService.warning('Unesite datum i vreme');
      }else {
        this._alertService.warning('Neispravno uneti podaci');
      }
    }
  }

}
