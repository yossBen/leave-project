import {Injectable} from '@angular/core';

import {User} from '../_models/index';
import {JwtService} from './jwt.service';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class UserService {
  constructor(private http: HttpClient, private jwtService: JwtService) {
  }

  getAll(): Observable<User[]> {
    return this.http.get<User[]>('/api/user/getAll');
  }

  create(user: User) {
    return this.http.post('/api/user/create', user, {responseType: 'text'});
  }

  getById(id: number): Observable<User> {
    return this.http.get<User>('/api/user/' + id);
  }

  update(user: User) {
    return this.http.put('/api/user/' + user.email, user);
  }

  delete(id: number) {
    return this.http.delete('/api/user/' + id);
  }
}
