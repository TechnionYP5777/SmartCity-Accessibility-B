import {Injectable} from "@angular/core";
import { Http, Headers,RequestOptions } from "@angular/http";
import { Constants } from "../constants";
import 'rxjs/add/operator/map';

@Injectable()
export class AdminService {
	headers: any;
	
    constructor(public http: Http) {
        this.http = http;
		try {
			var token = JSON.parse(window.sessionStorage.getItem('token')).token;
		} catch(err){
			token = "no token";
		}
		this.headers = new Headers();
		this.headers.append('Content-Type', 'application/x-www-form-urlencoded');
		this.headers.append('authToken',token);
    }

	getUserProfile() {
		
		return this.http.get(Constants.serverAddress +'/adminInfo', {headers: this.headers}).map(res=>res.json());
	}
	
	helpfulUsers(n) {

		return this.http.get(Constants.serverAddress + '/helpfulUsers?numOfUsers=' + n, {headers: this.headers}).map(res=>res.json());
	}

}