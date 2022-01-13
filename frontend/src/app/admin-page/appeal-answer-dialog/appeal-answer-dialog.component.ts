import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AdminAnswer } from 'src/app/model/admin-answer';
import { ReportAppealAnswer } from 'src/app/model/report-appeal-answer';
import { MyUserService } from 'src/app/service/my-user.service';

@Component({
  selector: 'app-appeal-answer-dialog',
  templateUrl: './appeal-answer-dialog.component.html',
  styleUrls: ['./appeal-answer-dialog.component.css']
})
export class AppealAnswerDialogComponent implements OnInit {

  appealId: number = 0;
  answer: ReportAppealAnswer = new ReportAppealAnswer();

  constructor(public dialogRef: MatDialogRef<AppealAnswerDialogComponent>, private _myUserService: MyUserService) { }

  ngOnInit() {
  }

  answerAppeal(){
    this._myUserService.sendAppealResponse(this.appealId, this.answer).subscribe(
      (ok: Boolean) => {
        this.dialogRef.close();
      },
      (error) => {
        // console.log(error)
      }
    )
  }

}
