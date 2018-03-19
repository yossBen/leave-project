///<reference path="../../../node_modules/@angular/common/http/src/client.d.ts"/>
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Day} from '../_models/index';

@Injectable()
export class AgendaService {
  constructor(private http: HttpClient) {
  }

  create(day: Day) {
    return this.http.post('/api/dayOff/create', {date: day.date}, {responseType: 'text'});
  }

  delete(day: Day) {
    return this.http.delete('/api/dayOff/delete', {responseType: 'text', params: {date: day.date}});
  }

  get(beginDate: Day, endDate: Day) {
    return this.http.get<Day[]>('/api/dayOff/get', {params: {beginDate: beginDate.date, endDate: endDate.date}});
  }
}
