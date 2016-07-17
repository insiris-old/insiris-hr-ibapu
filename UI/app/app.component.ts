import { Component, OnInit } from '@angular/core';
import { HTTP_PROVIDERS } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';

import { IJob } from './Job/job';
import { JobService } from './Job/job.service';

@Component({
    selector: 'my-app',
    templateUrl: 'app/app.component.html',
    styleUrls: ['app/app.component.css'],
    providers: [HTTP_PROVIDERS, JobService]
})
export class AppComponent implements OnInit{
    errorMessage: string;
    jobs: IJob[];

    constructor(private _jobService: JobService) {

    }

    toggleImage(): void {
        alert("hello");
    }

    deleteJob(id: number): void {
        console.log("Delete Job: " + id);
        this._jobService.deleteJob(id)
            .subscribe(error =>  this.errorMessage = <any>error);
            console.log("Reload data");
        this.getJobs();
    }

    getJobs(): void {
        this._jobService.getJobs()
                     .subscribe(
                       jobs => this.jobs = jobs,
                       error =>  this.errorMessage = <any>error);
    }
    
    ngOnInit(): void {
            this.getJobs();
            let newjob : IJob = {job_id:  13 , customerName:"FEname",notes:"DEnote",address:"FEaddfress"};
            console.log("---------------------->" +JSON.stringify(newjob));

            this._jobService.addJob(newjob)
            .subscribe(error =>  this.errorMessage = <any>error);

            
    }
 }
