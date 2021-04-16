import { Component, OnInit } from '@angular/core';
import { FlashMessageService } from './services/flash-message.service';
import { FlashMessage } from './models/flash-message';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {

  title = 'Travel Planner';
  snackbar: HTMLElement;
  messages: Array<any> = [];
  counter = 0;

  constructor(
    private flashMessageService: FlashMessageService
  ) { }

  ngOnInit(): void {
    this.snackbar = document.getElementById('snackbar');

    this.flashMessageService.getMessages().subscribe((message: FlashMessage) => {
      const current = this.counter++;
      this.messages.push({
        ...message,
        count: current
      });
      setTimeout(() => {
        this.messages = this.messages.filter(msg => msg.count !== current);
      }, message.time);
    });
  }

}







