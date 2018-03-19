import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {AlertService, AuthenticationService} from '../_services/index';

@Component({
  moduleId: module.id.toString(),
  templateUrl: 'login.component.html'
})

export class LoginComponent implements OnInit {
  model: any = {};
  loading = false;
  returnUrl: string;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private authenticationService: AuthenticationService,
              private alertService: AlertService) {
  }

  ngOnInit() {
    // reset login status
    this.authenticationService.logout();

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  login() {
    this.loading = true;
    this.authenticationService.login(this.model.email, this.model.password)
      .subscribe(
        data => {
          if (data && data.token) {
            // store user details and jwt token in local storage to keep user logged in between page refreshes
            sessionStorage.setItem('currentUser', JSON.stringify(data));
            this.router.navigate([this.returnUrl]);
          }
        },
        error => {
          let message;
          if (error.status === 400) {
            message = 'Email ou mots de passe sont incorrectes';
          } else {
            message = error.error.message;
          }
          this.alertService.error(message);
          this.loading = false;
        });
  }
}
