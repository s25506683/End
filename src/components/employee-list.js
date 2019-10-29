import React, {Component} from 'react';
import Employee from './employee';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import MyMenu from './menu';
import Paper from '@material-ui/core/Paper';
import useStyles from "./styles.js";
import { withStyles } from "@material-ui/core/styles";

class EmployeeList extends Component {
  state = {
    employees: [
     {id:"0", name:"Ben", department:"IT"},
     {id:"1", name:"Rich", department:"Marketing"},
     {id:"2", name:"Ruby", department:"Management"},
     {id:"3", name:"Judy", department:"IT"},
     {id:"4", name:"Rain", department:"IT"}
    ]
   } 

 render() {
  const { classes } = this.props;
  return (
  <div>
  <MyMenu/>
  <Paper className={classes.main}>
    <Table className={classes.table}>
      <TableHead>
        <TableRow>
          <TableCell>ID</TableCell>
          <TableCell>Name</TableCell>
          <TableCell>Department</TableCell>
        </TableRow>
      </TableHead>
      <TableBody>
      { this.state.employees.map((employee, index) => <Employee key={index} employee={employee}/>)}
      </TableBody> 
    </Table>
  </Paper>
  </div>
  )
 }

}
export default withStyles(useStyles) (EmployeeList); 