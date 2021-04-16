import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { FlashMessage } from '../models/flash-message';

@Injectable({
  providedIn: 'root'
})
export class FlashMessageService {

  constructor() { }

  message = new Subject<FlashMessage>();

  getMessages(): Subject<FlashMessage> {
    return this.message;
  }

  sendMessage(message: string, type: 'alert'|'success'|'info'|'danger' = 'info', title?: string, time: number = 5000): void {
    this.message.next({message, title, time, type});
  }

}
