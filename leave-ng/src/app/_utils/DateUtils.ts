export class DateUtils {
    static WEEK_DAYS: string[] = ['DI', 'LU', 'MA', 'ME', 'JE', 'VE', 'SA'];
    static MONTHS: string[] = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];

    public  static getDaysNumberOnMonth(date: Date): number {
        return new Date(date.getFullYear(), date.getMonth() + 1, -1).getDate() + 1;
    }
 }
