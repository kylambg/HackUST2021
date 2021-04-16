import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { LoginInfo } from '../../models/auth';
import { FlashMessageService } from '../../services/flash-message.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styles: [
  ]
})
export class LoginComponent implements OnInit {

  showRegister = false;

  loginData: LoginInfo = {
    username: '',
    password: ''
  };

  registerData: any = {
    username: '',
    password: '',
    email: '',
    first_name: '',
    last_name: '',
    repeat_password: ''
  };

  remember = false;

  constructor(
    private authService: AuthService,
    private fmService: FlashMessageService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  toggleRegister(): void {
    this.showRegister = !this.showRegister;
  }

  login(): void {
    this.authService.login(this.loginData, this.remember)
      .then(() => {
        this.fmService.sendMessage('Logged in.', 'success');
        this.router.navigate(['/']);
      })
      .catch((reject: Error) => this.fmService.sendMessage(reject.message, 'danger', 'Cannot Login'));
  }

  register(): void {
    if (this.registerData.first_name.length === 0 || this.registerData.last_name.length === 0){
      return this.fmService.sendMessage('First name and Last name are required.', 'danger');
    }
    if (this.registerData.email.length === 0){
      return this.fmService.sendMessage('E-mail is required.', 'danger');
    }
    if (this.registerData.username.length === 0){
      return this.fmService.sendMessage('Username is required.', 'danger');
    }
    if (this.registerData.password.length === 0 || this.registerData.password !== this.registerData.repeat_password){
      return this.fmService.sendMessage('Password cannot be blank and needs to match repeat password.', 'danger');
    }
    this.authService.register(this.registerData)
      .then(() => {
        this.fmService.sendMessage('Registered.', 'success');
        this.registerData = {
          username: '',
          password: '',
          email: '',
          first_name: '',
          last_name: '',
          repeat_password: ''
        };
        this.showRegister = false;
      })
      .catch((reject: Error) => this.fmService.sendMessage(reject.message, 'danger', 'Cannot Login'));
  }

}
