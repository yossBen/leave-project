import {Component} from '@angular/core';
import {Router} from '@angular/router';

import {AlertService, UserService} from '../_services/index';
import {Utils} from '../_utils/Utils';

@Component({
  moduleId: module.id.toString(),
  templateUrl: 'register.component.html'
})

export class RegisterComponent {
  model: any = {};
  loading = false;

  constructor(private router: Router,
              private userService: UserService,
              private alertService: AlertService) {
  }

  register() {
    this.loading = true;
    this.userService.create(this.model)
      .subscribe(
        data => {
          this.alertService.success('Registration successful', true);
          this.router.navigate(['/login']);
        },
        error => {
          let err = Utils.getCustomError(error);
          if (err && err.devStatus == "#400-1") {
            this.alertService.error("Un compte existe déjà pour cette adresse email");
          }
          console.log(error.message);
        },
        () => {
          this.loading = true;
        });
  }
}
