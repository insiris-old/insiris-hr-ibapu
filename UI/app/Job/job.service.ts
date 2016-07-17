import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';

import { IJob } from './Job';

@Injectable()
export class JobService {
    private _productUrl = 'http://localhost:9000';
    private _body = "username=" + "" + "&password=" + "";

    constructor(private _http: Http) { }

    getJobs(): Observable<IJob[]> {
        return this._http.get(this._productUrl)
            .map((response: Response) => <IJob[]> response.json())
            .do(data => console.log('All: ' +  JSON.stringify(data)))
            .catch(this.handleError);
    }

    deleteJob(id: number): Observable<IJob[]> {
         console.log("---------------------->" + this._productUrl + "?job_id=" + id);
        return this._http.delete(this._productUrl + "?job_id=" + id)
            .catch(this.handleError);
    }

    addJob(job : IJob): Observable<IJob[]> {
        let body = "";
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this._http.post(this._productUrl + "?job_id=15&customerName=ikram&notes=updateNote&address=testAddress", body, options)
            .catch(this.handleError);
    }

    editJob(job : IJob): Observable<IJob[]> {
        let body = JSON.stringify(job);
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this._http.put(this._productUrl, body, options)
            .catch(this.handleError);
    }

    getJob(id: number): Observable<IJob> {
        return this.getJobs()
            .map((products: IJob[]) => products.find(j => j.job_id === id));
    }

    private handleError(error: Response) {
        console.log("---------------------->" +error);
        return Observable.throw(JSON.stringify(error)|| 'Server error');
    }
}