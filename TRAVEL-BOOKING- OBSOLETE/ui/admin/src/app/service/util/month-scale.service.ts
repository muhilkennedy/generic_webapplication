import { Injectable } from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class SeasonsUtil {

  static getMonthName(monthNumber: number) {
    const date = new Date();
    date.setDate(19);
    date.setMonth(monthNumber - 1);
    date.setFullYear(1996);
    // Using the browser's default locale.
    return date.toLocaleString([], { month: 'long' });
  }

  static getMonthScaleMap(): MonthScaleMap[] {
    let months = new Array();
    for(var i=1; i<=12; i++){
      months.push(new MonthScaleMap(i,0));
    }
    return months;
  }

}

export class MonthScaleMap{

  month: number;
  scale: number;

  constructor(month, scale){
    this.month = month;
    this.scale = scale;
  }

}
