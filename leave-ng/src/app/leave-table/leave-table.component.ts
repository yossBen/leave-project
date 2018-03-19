import {Component, OnInit} from '@angular/core';
import {Day} from '../_models/index';
import {DateUtils} from '../_utils/index';
import {AgendaService, AlertService} from '../_services/index';

@Component({
  selector: 'app-leave-table',
  templateUrl: './leave-table.component.html',
  styleUrls: ['./leave-table.component.css']
})
export class LeaveTableComponent implements OnInit {
  static ACTIVE_CLASS = 'active';
  days: Day[];
  month: number;
  year: number;

  constructor(private agendaService: AgendaService, private alertService: AlertService) {
    const currentDate = new Date();
    this.month = currentDate.getMonth();
    this.year = currentDate.getFullYear();
  }

  ngOnInit() {
    this.loadDays();
  }

  loadDays(): void {
    this.days = new Array();
    const date = new Date(this.year, this.month);
    const daysNumberOnMonth: number = DateUtils.getDaysNumberOnMonth(date);

    let firstDay: number = date.getDay();
    if (firstDay === 0) {
      firstDay = 7;
    }
    // index =1 est le jour de Lundi
    for (let index = 2; index <= firstDay; index++) {
      this.days.push(new Day(new Date(this.year, this.month, -firstDay + index)));
    }

    for (let index = 1; index <= daysNumberOnMonth; index++) {
      this.days.push(new Day(new Date(this.year, this.month, index)));
    }

    this.agendaService.get(this.days[0], this.days[this.days.length - 1]).subscribe(
      (daysOff) => {
        this.displayDaysOff(daysOff);
      },
      error => {
        this.alertService.error(error.message);
      });
  }

  displayDaysOff(daysOff: any): void {
    for (let i = 0; i < daysOff.length; i++) {
      this.days.forEach(function (day: Day) {
        if (day.date === daysOff[i].date) {
          day.isDayOff = true;
        }
      });
    }
  }

  changeYear(year: number) {
    this.year = year;
    this.loadDays();
  }

  changeMonth(month: number) {
    this.month = month;
    this.loadDays();
  }

  addDayOff(event: Event, day: Day) {
    if (!day.isDayOff) {
      this.agendaService.create(day).subscribe(
        (data) => {
          event.srcElement.parentElement.classList.add(LeaveTableComponent.ACTIVE_CLASS);
          day.isDayOff = true;
        },
        error => {
          this.alertService.error(error.message);
        });
    } else {
      this.agendaService.delete(day).subscribe(
        (data) => {
          event.srcElement.parentElement.classList.remove(LeaveTableComponent.ACTIVE_CLASS);
          day.isDayOff = false;
        },
        error => {
          this.alertService.error(error.message);
        });
    }
  }

  get months(): string[] {
    return DateUtils.MONTHS;
  }

  get years(): number[] {
    const y = new Date().getFullYear();
    return [y - 1, y, y + 1];
  }

  get weekDays(): string[] {
    return [DateUtils.WEEK_DAYS[1], DateUtils.WEEK_DAYS[2], DateUtils.WEEK_DAYS[3]
      , DateUtils.WEEK_DAYS[4], DateUtils.WEEK_DAYS[5], DateUtils.WEEK_DAYS[6], DateUtils.WEEK_DAYS[0]];
  }
}
