import {Injectable} from '@angular/core';
import 'rxjs/add/operator/map';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from '../_models/user';
import {Observable} from 'rxjs/Observable';
import * as querystring from 'querystring';

@Injectable()
export class AuthenticationService {
  // pour palier au probleme d'injection dans l'interceptor
  public static getToken(): string {
    const user: User = JSON.parse(sessionStorage.getItem('currentUser'));
    if (user) {
      return user.token;
    }
    return null;
  }

  constructor(private http: HttpClient) {
  }

  login(email: string, password: string): Observable<User> {
    return this.http.post<User>('/api/authenticate/login', {email: email, password: password});
  }

  validateAccount(token: string): Observable<any> {
    return this.http.post ('/api/authenticate/validate', {token: token});
  }

  logout() {
    // remove user from local storage to log user out
    sessionStorage.removeItem('currentUser');
  }
}
