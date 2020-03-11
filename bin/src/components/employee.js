import React, {Component} from 'react';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
 
export default class Employee extends Component{

  render(){
    return (
      <TableRow>
        <TableCell>{this.props.employee.id}</TableCell>
        <TableCell>{this.props.employee.name}</TableCell>
        <TableCell> {this.props.employee.department}</TableCell>
      </TableRow>
    )
    
  }
    
}