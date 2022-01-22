import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {House} from "../model/house";
import {Boat} from "../model/boat";
import {BoatHomeSlide} from "../model/boat-home-slide";
import {ReservationCheck} from "../model/reservation-check";

@Injectable({
  providedIn: 'root'
})
export class BoatService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/boat';

  public getBoatById(id: number): Observable<Boat>{
    return this._http.get<Boat>(`${this.userPath}/getBoatById/`+id)
  }

  public findAll(): Observable<Boat[]>{
    return this._http.get<Boat[]>(`${this.userPath}/findAll`)
  }

  public findAllBoatsForHomePage(): Observable<BoatHomeSlide[]>{
    return this._http.get<BoatHomeSlide[]>(`${this.userPath}/findBoatsForHomePage`)
  }

  public edit(boat: Boat): Observable<Boat>{
    return this._http.put<Boat>(`${this.userPath}/edit/`+ boat.id, boat)
  }

  public delete(id: number): Observable <boolean> {
    return this._http.delete<boolean>(`${this.userPath}/delete/` + id)
  }

  public save(boat: Boat): Observable<Boat> {
    return this._http.post<Boat>(`${this.userPath}/add`, boat)
  }

  public findAllAvailableBoats(request: ReservationCheck): Observable<Boat[]>{
    return this._http.post<Boat[]>(`${this.userPath}/findAllAvailableBoats`, request)
  }

  public deleteAllBoatsByOwner(id: number): Observable <boolean> {
    return this._http.delete<boolean>(`${this.userPath}/deleteAllBoatsByOwner/` + id)
  }
}
