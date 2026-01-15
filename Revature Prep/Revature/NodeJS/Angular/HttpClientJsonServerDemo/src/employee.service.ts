import {Injectable} from '@angular/core';
// @ts-ignore
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {catchError, Observable, retry, throwError} from 'rxjs';
import {Employee} from './employee';

@Injectable({providedIn: 'root'})
export class EmployeeService {
  baseurl = 'http://localhost:3000/employees';

  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  }

  // POST
  CreateEmployee(data: any): Observable<Employee> {
    return this.http.post<Employee>(this.baseurl, data, this.httpOptions)
      .pipe(retry(1), catchError(this.errorHandler));
  }

  // GET
  GetEmployees(id: number): Observable<Employee> {
    return this.http.get<Employee>(this.baseurl + id)
      .pipe(retry(1), catchError(this.errorHandler));
  }

  // PUT
  UpdateEmployee(id: number, data: any): Observable<Employee> {
    return this.http.put<Employee>(this.baseurl + id, JSON.stringify(data), this.httpOptions)
      .pipe(retry(1), catchError(this.errorHandler));
  }

  // DELETE
  DeleteEmployee(id: number) {
    return this.http.delete(this.baseurl + id, this.httpOptions)
      .pipe(retry(1), catchError(this.errorHandler));
  }

  errorHandler(error: { error: { message: string; }; status: any; message: any; }) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Client Side Error
      errorMessage = error.error.message;
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }

    console.log(errorMessage);
    return throwError(errorMessage);
  }
}
