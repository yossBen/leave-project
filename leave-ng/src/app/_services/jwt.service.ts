import {Injectable} from '@angular/core';
import {Headers, RequestOptions} from '@angular/http';

@Injectable()
export class JwtService {
  getJwt() {
    // create authorization header with jwt token
    const currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
    if (currentUser && currentUser.token) {
      const headers = new Headers({'Authorization': 'Bearer ' + currentUser.token});
      return new RequestOptions({headers: headers});
    }
  }
}
