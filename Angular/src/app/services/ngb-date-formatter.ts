import { Injectable } from '@angular/core';
import { NgbDateParserFormatter, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';


@Injectable()
export class NgbDateFormatter extends NgbDateParserFormatter {

  months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

  parse(value: string): NgbDateStruct {
    throw new Error('Method not implemented.');
  }

  format(date: NgbDateStruct | null): string | null {
    return date ? this.pad(date.day, 2) + '-' + this.months[date.month - 1] + '-' + date.year : null;
  }

  pad(num, size) {
    let s = num + '';
    while (s.length < size) { s = '0' + s; }
    return s;
  }

}
