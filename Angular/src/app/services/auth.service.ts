import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { LoginInfo, UserInfo } from '../models/auth';
import { FlashMessageService } from './flash-message.service';
import { EndpointsService } from './endpoints.service';
import { Observable, of } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private router: Router,
    private http: HttpClient,
    private fmService: FlashMessageService,
    private endpoints: EndpointsService,
  ) { }

  loginInfo: LoginInfo;
  userInfo: UserInfo;
  refresh: string;
  access: string;

  private static urlBase64Decode(str: string): string {
    let output = str.replace(/-/g, '+').replace(/_/g, '/');
    switch (output.length % 4) {
      case 0:
        break;
      case 2:
        output += '==';
        break;
      case 3:
        output += '=';
        break;
      default:
        throw new Error('Illegal base64url string!');
    }
    return decodeURIComponent((window as any).escape(window.atob(output)));
  }

  private static decodeJwt(token: string): any {
    if (token === null || token === '') { return { upn: '' }; }
    const parts = token.split('.');
    if (parts.length !== 3) {
      throw new Error('JWT must have 3 parts');
    }
    parts.pop();
    const decoded = AuthService.urlBase64Decode(parts[1]);
    if (!decoded) {
      throw new Error('Cannot decode the token');
    }
    return JSON.parse(decoded);
  }

  private static isTokenValid(token: string): boolean {
    const expiry = new Date(AuthService.decodeJwt(token).exp * 1000);
    const now = new Date();
    return expiry > now;
  }


  register(registerInfo: any): Promise<any>  {
    return this.http.post(this.endpoints.register, registerInfo).toPromise();
  }

  login(loginInfo: LoginInfo, remember: boolean): Promise<any> {

    return this.http.post(this.endpoints.getToken, loginInfo).toPromise()
      .then((response: any) => {
        if (remember) {
          localStorage.setItem('loginInfo', JSON.stringify(loginInfo));
        }
        this.refresh = response.refresh;
        this.access = response.access;
        this.userInfo = AuthService.decodeJwt(response.refresh).user;
        localStorage.setItem('userInfo', JSON.stringify(this.userInfo));
        localStorage.setItem('refreshToken', this.refresh);
      })
      .catch((error) => {
        if (error.status === 401) {
          throw new Error('Your login credentials seem invalid. Please try again.');
        } else {
          throw new Error('There seems to be an issue with the server. Please try again. ' +
            'If problem persists, contact site administrator for assistance.');
        }
      });

  }

  logout(): Promise<null | string> {
    return new Promise((resolve) => {
      this.loginInfo = undefined;
      this.userInfo = undefined;
      this.refresh = undefined;
      this.access = undefined;
      localStorage.removeItem('userInfo');
      localStorage.removeItem('loginInfo');
      localStorage.removeItem('token');
      this.router.navigate(['/login']);
      resolve(null);
    });
  }

  getAuth(): any {
    if (!this.userInfo) {

      const loginInfoStored = localStorage.getItem('loginInfo');
      const userInfoStored = localStorage.getItem('userInfo');
      const refreshStored = localStorage.getItem('refreshToken');

      if (userInfoStored && (loginInfoStored || AuthService.isTokenValid(refreshStored))) {

        this.userInfo = JSON.parse(userInfoStored);

        if (loginInfoStored) {

          this.loginInfo = JSON.parse(loginInfoStored);

          if (AuthService.isTokenValid(refreshStored)) {
            this.refresh = refreshStored;
            this.getAccessToken().subscribe();
          } else {
            this.login(this.loginInfo, true)
              .catch(() => {
                this.fmService.sendMessage('You have been logged out. Please login again.', 'danger');
                this.logout().then(() => null);
                this.router.navigate(['/login']);
              });
          }

        } else {

          this.refresh = refreshStored;
          this.getAccessToken().subscribe();

        }


      }

    }

    return this.userInfo;
  }

  getAccessToken(): Observable<any> {
    if (this.access && AuthService.isTokenValid(this.access)) {
      return of(this.access);
    } else {
      return this.http.post(this.endpoints.refreshToken, { refresh: this.refresh })
        .pipe(
          map(
            (res: any) => {
              this.access = res.access;
              return res.access;
            },
            (err: HttpErrorResponse) => {
              if (err.error.code === 'token_not_valid' && this.loginInfo) {
                return this.login(this.loginInfo, false);
              } else {
                this.logout().then(() => null);
                return 'You have been logged out due to inactivity. Please login and try again.';
              }
            }
          )
        );
    }

  }

  private authorized_http(url: string, params?: any, body?: any, method: string = 'get'): Observable<any> {
    return this.getAccessToken().pipe(
      switchMap(
        token => {
          const options: any = {
            headers: new HttpHeaders({
              Authorization: 'Bearer ' + token
            })
          };
          if (params) {
            options.params = params;
          }
          let request: any;
          if (method === 'get' || method === 'delete') {
            request = this.http[method](url, options);
          } else {
            request = this.http[method](url, body, options);
          }
          return request;
        },
      )
    );
  }

  getRequest = (url: string, params?: any) => this.authorized_http(url, params, null, 'get');

  deleteRequest = (url: string, params?: any) => this.authorized_http(url, params, null, 'delete');

  postRequest = (url: string, params?: any, body?: any) => this.authorized_http(url, params, body, 'post');

  putRequest = (url: string, params?: any, body?: any) => this.authorized_http(url, params, body, 'put');

  changePassword(oldPassword: string, newPassword: string): Observable<any> {
    return this.putRequest(this.endpoints.changePassword, null, {
      old_password: oldPassword,
      new_password: newPassword
    });
  }

}
