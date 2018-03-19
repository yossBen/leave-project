import {Injectable} from '@angular/core';
import 'rxjs/add/operator/do';
import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {AuthenticationService} from '../_services/authentication.service';
import {Router} from '@angular/router';
import {environment} from '../../environments/environment';
import * as querystring from "querystring";

// import { environment } from '../../environments/environment';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor(private router: Router) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = AuthenticationService.getToken();
    // Request
     req = req.clone({
      url: `${environment.SERVER}${req.url}`,
      body: typeof req.body === 'object' ? querystring.stringify(req.body) : req.body,
      setHeaders: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    });
    if (token) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    // Response
    return next.handle(req).do(
      (event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          console.log('Bonne Request');
        }
      }, (err: any) => {
        if (err instanceof HttpErrorResponse) {
          if (err.status === 401) {
            console.log('Mauvaise Request');
            this.router.navigate(['/login']);
          }
        }
      }
    );
  }
}
