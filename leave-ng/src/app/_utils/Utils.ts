export class Utils {
  public static getCustomError(data: any): any {
    var err = data.error;
    if (err) {
      if (typeof err == "string") {
        err = JSON.parse(err);
      }
    }
    return err;
  }
}
