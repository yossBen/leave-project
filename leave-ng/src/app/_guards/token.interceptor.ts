import {Injectable} from '@angular/core';
import 'rxjs/add/operator/do';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
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
    // Request
    req = req.clone({
      url: `${environment.SERVER}${req.url}`,
      body: typeof req.body === 'object' ? querystring.stringify(req.body) : req.body,
      setHeaders: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    });

    // Ajouter l'Authentification JWT
    const token = AuthenticationService.getToken();
    if (token) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    // Response
    return next.handle(req).do(
      (err: any) => {
        if (err instanceof HttpErrorResponse) {
          if (err.status === 401) {
            console.log("Probleme d'authentification");
            this.router.navigate(['/login']);
          }
        }
      }
    );
  }
}
