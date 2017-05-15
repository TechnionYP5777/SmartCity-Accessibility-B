import {Injectable} from "@angular/core";
import { Http, Headers,RequestOptions } from "@angular/http";
import { Constants } from "../constants";
import 'rxjs/add/operator/map';

@Injectable()
export class UserInformationService {
    constructor(public http: Http) {
        this.http = http;
    }

	getUserProfile() {
		var token = window.sessionStorage.getItem('token');
		if(token == null)
			token = "no token";
		var headers = new Headers();
		headers.append('Content-Type', 'application/x-www-form-urlencoded');
		headers.append('authToken',token);
		return this.http.get(Constants.serverAddress +'/userInfo/name', {headers: headers}).map(res=>res.json());
	}
	
	getEasyJsonExample() {
		return this.http.get(Constants.serverAddress +'/userInfo/JSONEXAMPLE').map(res=>res.json());
	}
}