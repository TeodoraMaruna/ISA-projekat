import { Component, OnInit } from '@angular/core';
import {MatRadioChange} from "@angular/material/radio";
import {HouseService} from "../../service/house.service";
import {MyUser} from "../../model/my-user";
import {House} from "../../model/house";
import {Router} from "@angular/router";
import * as _ from 'underscore';

@Component({
  selector: 'app-home-house',
  templateUrl: './home-house.component.html',
  styleUrls: ['./home-house.component.css']
})
export class HomeHouseComponent {
  houses: House[] = new Array();
  housesFilter: House[] = new Array();
  houseSearch: House[] = new Array();
  houseName: number = 0;
  houseGrade: number = 0;
  housePrice: number = 0
  houseFilterGrade: number = 0;
  houseNameSearch: string = "";
  houseAddressSearch: string = "";

  constructor(private _houseService: HouseService, private _router: Router) { }

  ngOnInit(): void {
    this.loadData()
  }

  changeSortName() {
    if(this.houseName === 1){
      this.houses = _.sortBy(this.houses, 'name',);
    }else if(this.houseName ===2){
      this.houses = _.sortBy(this.houses, 'name',).reverse();
    }
  }

  changeSortPrice() {
    if(this.housePrice === 1){
      console.log("uslo")
      this.houses = _.sortBy(this.houses, 'pricePerDay',);
    }else if(this.housePrice ===2 ){
      this.houses = _.sortBy(this.houses, 'pricePerDay',).reverse();
    }
  }

  changeSortGrade() {
    if(this.houseGrade === 1){
      this.houses = _.sortBy(this.houses, 'avarageGrade',);
    }else if(this.houseGrade ===2){
      this.houses = _.sortBy(this.houses, 'avarageGrade',).reverse();
    }
  }

  radio1Changed() {
    var sortedArray = _(this.houses).chain().sortBy(function(house) {
      return house.name;
    }).sortBy(function(house) {
      return house.address;
    }).value();
  }

  private loadData() {
    this._houseService.findAll().subscribe(   // subscribe - da bismo dobili odgovor beka
      (houses: House[]) => {
        this.houses = houses;
        this.housesFilter = houses
        this.houseSearch = houses
        console.log(this.houses)
      },
      (error) => {
      },
    )
  }

  goToHouse(id: number) {

    this._router.navigate(['/house',id])
  }

  searchHouses(){

    this.houses = this.houseSearch.filter(s => s.name.toLowerCase().includes(this.houseNameSearch.toLowerCase()) &&
      (s.address.street+" "+ s.address.city + " " + s.address.state).toLowerCase().includes(this.houseAddressSearch.toLowerCase()))
    this.housesFilter = this.houses;
  }

  filterGrade() {

    if(this.houseFilterGrade == 5){
      this.houses = this.housesFilter.filter(s => s.avarageGrade == 5 )
    }else if(this.houseFilterGrade == 4) {
      this.houses = this.housesFilter.filter(s => s.avarageGrade >= 4 && s.avarageGrade < 5)
    }else if(this.houseFilterGrade == 3) {
      this.houses = this.housesFilter.filter(s => s.avarageGrade >= 3 && s.avarageGrade < 4)
    }else if(this.houseFilterGrade == 2) {
      this.houses = this.housesFilter.filter(s => s.avarageGrade >= 2 && s.avarageGrade < 3)
    }else if(this.houseFilterGrade == 1) {
      this.houses = this.housesFilter.filter(s => s.avarageGrade >= 1 && s.avarageGrade < 2)
    }else if(this.houseFilterGrade == 0) {
      this.loadData()
    }

    }


}
