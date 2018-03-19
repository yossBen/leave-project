import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationService} from "../_services/authentication.service";
import {AlertService} from "../_services/alert.service";

@Component({
  selector: 'app-account-validate',
  templateUrl: './account-validate.component.html',
  styleUrls: ['./account-validate.component.css']
})
export class AccountValidateComponent implements OnInit {
  validate: Boolean = null;

  constructor(private route: ActivatedRoute, private alertService: AlertService, private router: Router,
              private authenticationService: AuthenticationService) {
  }

  ngOnInit(): void {
    const token = this.route.snapshot.queryParams['token'];
    if (token) {
      this.authenticationService.validateAccount(token)
        .subscribe(
          data => {
            if (data.validate) {
              this.alertService.success('Account Validate');
              this.validate = true;
            } else {
              this.alertService.error('Account InValidate');
              this.validate = false;
            }
          },
          error => {
            this.alertService.error(error.message);
          });
    }
  }
}
