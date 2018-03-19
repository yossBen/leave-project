import {DateUtils} from '../_utils/index';

export class Day {
  date: string;
  number: number;
  name: string;
  isWeekend = false;
  isPublicHoliday = false;
  isDayOff = false;

  constructor(date: Date) {
    this.format(date);
    this.number = date.getDate();
    this.name = DateUtils.WEEK_DAYS[date.getDay()];
    if (date.getDay() === 0 || date.getDay() === 6) {
      this.isWeekend = true;
    }
  }

  private format(date: Date) {
    const month = date.getMonth() + 1;
    const day = date.getDate();
    this.date = date.getFullYear() + '-' + ( month < 10 ? '0' : '') + month + '-' + (day < 10 ? '0' : '') + day;
  }
}
