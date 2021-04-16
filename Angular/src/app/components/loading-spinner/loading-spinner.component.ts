import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-loading-spinner',
  templateUrl: './loading-spinner.component.html',
  styleUrls: ['./loading-spinner.component.scss']
})
export class LoadingSpinnerComponent implements OnInit {

  @Input() smallMargin = false;

  margin = '100px auto 100px auto';

  constructor() { }

  ngOnInit(): void {
    this.margin = '100px auto 100px auto';
    if (this.smallMargin) {
      this.margin = '20px auto 40px auto';
    }
  }

}
