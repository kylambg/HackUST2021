import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { UserInfo } from '../../models/auth';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
})
export class MainComponent implements OnInit {

  userInfo: UserInfo;

  constructor(
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.userInfo = this.authService.getAuth();
  }

  logout(): void {
    this.authService.logout();
  }

}
