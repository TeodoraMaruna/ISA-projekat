import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Company } from 'src/app/model/company';
import { UserCategory } from 'src/app/model/user-category';
import { CompanyService } from 'src/app/service/company.service';

@Component({
  selector: 'app-edit-company-rules-dialog',
  templateUrl: './edit-company-rules-dialog.component.html',
  styleUrls: ['./edit-company-rules-dialog.component.css']
})
export class EditCompanyRulesDialogComponent implements OnInit {

  company: Company = new Company();
  userCategory: UserCategory = new UserCategory();

  ngOnInit(): void {
    this.loadData()
  }

  constructor(public dialogRef: MatDialogRef<EditCompanyRulesDialogComponent>, private _companyService: CompanyService) {
  }


  onNoClick(): void {
    this.dialogRef.close();
  }

  loadData(){
    this._companyService.getCompanyInfo().subscribe(
      (company: Company) => {
        this.company = company;
      }
    )
  }

  deleteCategory(id: number) {
    this._companyService.delete(id).subscribe(   // OBAVEZNO SE MORA SUBSCRIBE-OVATI !!!
      (boolean:boolean) =>{
        this.reloadServices()
      }
    )
  }

  editCategory(category: UserCategory) {
    this._companyService.edit(category).subscribe(   // OBAVEZNO SE MORA SUBSCRIBE-OVATI !!!
      (boolean:boolean) =>{
        this.reloadServices()
      }
    )
  }

  reloadServices(){
    this._companyService.getAllUserCategories().subscribe(
      (userCategories: UserCategory[]) => {
        this.company.userCategories = userCategories
      }
    )
  }

  addCategory(){
    if(this.userCategory.name != ''){
      this._companyService.saveCategory(this.userCategory).subscribe(   // subscribe - da bismo dobili odgovor beka
        (isAdded: Boolean) => {
          this.reloadServices();
          this.userCategory.name = '';
          this.userCategory.points = 0;
          this.userCategory.discountPercentage = 0;
        },
        (error) => {
          //this._alertService.danger('Doslo je do greske');
        },
      )
    }
  }

  save(){
    this._companyService.save(this.company).subscribe(   // subscribe - da bismo dobili odgovor beka
      (isSaved: Boolean) => {
        this.dialogRef.close();
      },
      (error) => {
        //this._alertService.danger('Doslo je do greske');
      },
    )
  }

}
