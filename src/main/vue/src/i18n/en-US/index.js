export default {
  appName: 'Authorization and Accounting System',
  channelsList: {
    title: 'Channels',
    channelDetailsTitle: 'Channel details',
    fields: {
      id: 'Id',
      name: 'Channel Name',
      description: 'Description',
      isActive: 'Is Active',
    },
    dialog: {
      header: {
        add: 'Add channel',
        modify: 'Modify channel'
      },
      validations: {
        nameMustPresent: 'Channel name must be set',
        nameLength: 'Channel name must be at least 3 characters long',
        nameLengthMax: 'Channel name must be less than 50 characters long'
      },
      confirmation: {
        delete: 'Are you sure you want to delete this channel?'
      }
    },
  },
  transactionsList: {
    id: 'Id',
    amount: 'Amount',
    date: 'Date',
    comment: 'Comment',
    terminal: 'Terminal',
    card: 'Card',
    merchant: 'Merchant',
    mcc: 'MCC',
    currency: 'Currency',
    respCode: 'Resp Code',
  },
  mappingsList: {
    title: 'Mappings',
    channelDetailsTitle: 'Mappings details',
    fields: {
      id: 'Id',
      field: 'Field',
      attribute: 'Attribute',
      description: 'Description',
      type: 'Type',
      stringFunctions: 'String Functions',
      dateFormat: 'Date Format',
      fromValue: 'Value to replace',
      toValue: 'Value to replace with',
      isMandatory: 'Is Mandatory',
      startIndex: 'Start Index',
      endIndex: 'End Index',
      isNominal: 'Is Nominal',
      currencyFrom: 'Currency From',
      currencyField: 'Currency Field',
      currencyFromRegister: 'Currency From Register',
      currencyToRegister: 'Convert to currency',
      concatDelimiter: 'Concat Delimiter',
      isActive: 'Is Active',
    },
    dialog: {
      header: {
        add: 'Add mapping',
        modify: 'Modify mapping'
      },
      validations: {
        fieldMustPresent: 'Field must be set',
        fieldLength: 'Field must be at least 3 characters long',
        fieldLengthMax: 'Field must be less than 50 characters long',
        attributeMustPresent: 'Attribute must be set',
        attributeLength: 'Attribute must be at least 3 characters long',
        attributeLengthMax: 'Attribute must be less than 50 characters long',
        descriptionLengthMax: 'Description must be less than 200 characters long',
        typeMustPresent: 'Type must be set',
        dateFormatMustPresent: 'Date format must be set',
        fromValueMustPresent: 'Value to replace must be set',
        fromValueLength: 'Value to replace must be at least 3 characters long',
        fromValueLengthMax: 'Value to replace must be less than 50 characters long',
        toValueMustPresent: 'Value to replace with must be set',
        toValueLength: 'Value to replace with must be at least 3 characters long',
        toValueLengthMax: 'Value to replace with must be less than 50 characters long',
        isMandatoryMustPresent: 'Is mandatory must be set',
        startIndexMustPresent: 'Start index must be set',
        startIndexMustBeNumber: 'Start index must be a number',
        endIndexMustBeNumber: 'End index must be a number',
        endIndexMustBeGreaterThanStartIndex: 'End index must be greater than start index',
        isNominalMustPresent: 'Is nominal must be set',
        currencyFromMustPresent: 'Currency from must be set',
        currencyFieldMustPresent: 'Currency field must be set',
        currencyFromRegisterMustPresent: 'Currency from register must be set',
        concatDelimiterMustPresent: 'Concat delimiter must be set',
        concatDelimiterLengthMax: 'Concat delimiter must be less than 3 characters long',
        stringFunctionsMustPresent: 'String functions must be set',
      },
      confirmation: {
        delete: 'Are you sure you want to delete this mapping?'
      }
    },
  },
  groupsList: {
    title: 'Groups',
    groupDetailsTitle: 'Group details',
    fields: {
      id: 'Id',
      name: 'Group Name',
      date: 'Date',
      placesCount: 'Places Count',
      booked: 'Booked',
      schedule: 'Schedule',
      placePrice: 'Place Price'
    },
    dialog: {
      header: {
        add: 'Add group',
        modify: 'Modify group'
      },
      validations: {
        nameMustPresent: 'Group name must be set',
        nameLength: 'Group name must be at least 3 characters long',
        nameLengthMax: 'Group name must be less than 50 characters long',
        dateMustPresent: 'Date must be set',
        dateMustBeInFuture: 'Date must be in future',
        placesCountMustPresent: 'Places count must be set',
        placesCountMustBePositive: 'Places count must be positive',
        placesCountMin: 'Places count must be at least 3',
        placesCountMax: 'Places count must be less than 100',
        bookedCannotBeMoreThanPlacesCount: 'Booked places count cannot be more than places count',
        scheduleMustPresent: 'Schedule must be set'
      },
      confirmation: {
        delete: 'Are you sure you want to delete this group?'
      }
    },
    widget: {
      paid: 'Paid',
      booked: 'Booked'
    }
  },
  userList: {
    title: 'Users',
    fields: {
      id: 'Id',
      username: 'Username',
      firstName: 'First Name',
      lastName: 'Last Name',
      password: 'Password',
      repeatPassword: 'Repeat Password',
      email: 'Email',
      validations: {
        usernameMustPresent: 'Username must be set',
        usernameLength: 'Username must be at least 3 characters long',
        usernameLengthMax: 'Username must be less than 20 characters long',
        emailMustPresent: 'Email must be set',
        emailMustBeValid: 'Email must be valid',
        passwordMustPresent: 'Password must be set',
        passwordMinLength: 'Password must be between 8 and 20 characters long',
        passwordMustBeTheSame: 'Passwords must be the same',
      }
    },
    dialog: {
      header: {
        add: 'Add user',
        modify: 'Modify user'
      }
    }
  },
  roleList: {
    title: 'Roles',
    fields: {
      id: 'Id',
      name: 'Role Name',
      desc: 'Description',
    },
    dialog: {
      header: {
        add: 'Add role',
        modify: 'Modify role'
      },
      validations: {
        nameMustPresent: 'Role name must be set',
        nameLength: 'Role name must be at least 3 characters long',
        nameLengthMax: 'Role name must be less than 50 characters long',
      },
      confirmation: {
        delete: 'Are you sure you want to delete this role?'
      }
    },
  },
  studentsList: {
    fields: {
      id: 'Id',
      name: 'Name',
      phone: 'Phone',
      amount: 'Amount',
      comment: 'Comment',
      paid: 'Paid'
    },
    dialog: {
      header: {
        add: 'Add student',
        modify: 'Modify group'
      },
      validations: {
        nameMustPresent: 'Student name must be set',
        nameLength: 'Student name must be at least 3 characters long',
        nameLengthMax: 'Student name must be less than 50 characters long',
        phoneMustPresent: 'Phone must be set',
        phoneLengthMax: 'Phone must be less than 12 characters long',
        phoneMustBeNumber: 'Phone must be a number',
        amountMustPresent: 'Amount must be set',
        commentLengthMax: 'Comment must be less than 200 characters long',
        amountMustBeNumber: 'Amount must be a number',
      },
      confirmation: {
        delete: 'Are you sure you want to delete this student?'
      }
    },
    buttons: {
      add: 'Add student',
    },
    backButton: 'Groups',
    exceptions: {
      studentWithPhoneAndNumberAlreadyAdded: 'Student with this phone and number already added',
      successfullyCreated: 'Student successfully created',
      groupStudentNotFound: 'Group not found',
      studentNotFound: 'Student not found',
    }
  },
  resourcesList: {
    title: 'Resources',
    detailsTitle: 'Resource details',
    fields: {
      id: 'Id',
      name: 'Name',
      description: 'Description',
      type: 'Type',
      calendar: 'Calendar',
      active: 'Active'
    },
    dialog: {
      header: {
        add: 'Add resource',
        modify: 'Modify resource'
      },
      validations: {
        nameMustPresent: 'Resource name must be set',
        nameLength: 'Resource name must be at least 3 characters long',
        nameLengthMax: 'Resource name must be less than 50 characters long',
        descriptionMustPresent: 'Description must be set',
        descriptionLengthMin: 'Description must be at least 3 characters long',
        descriptionLengthMax: 'Description must be less than 200 characters long',
        typeMustPresent: 'Type must be set',
        calendarMustPresent: 'Calendar must be set',
      },
      confirmation: {
        delete: 'Are you sure you want to delete this resource?'
      }
    },
    deleteConfirmation: 'Are you sure you want to delete this resource?',
    buttons: {
      add: 'Add resource',
    },
  },
  loginPage: {
    fields: {
      login: 'Username',
      password: 'Password',
      email: 'Email',
      formTitle: 'Sign in',
    },
    validations: {
      loginMustPresent: 'Login must be set',
      mailMustPresent: 'Email must be set',
      mailMustBeValid: 'Email must be valid',
      passwordMustPresent: 'Password must be set',
      passwordLength: 'Password must be between 8 and 20 characters long',
      sessionExpired: 'Session expired',
    },
    registration: {
      message: 'Don\'t have an account?',
      button: 'Register',
    }
  },
  registerPage: {
    fields: {
      login: 'Username',
      password: 'Password',
      email: 'Email',
      formTitle: 'Register',
      repeatPassword: 'Repeat password',
      policy: 'By clicking "Register" you accept the terms of the User Agreement, Privacy Policy, and Cookie Policy of Groups'
    },
    validations: {
      loginMustPresent: 'Login must be set',
      emailMustPresent: 'Email must be set',
      mailMustBeValid: 'Email must be valid',
      passwordMustPresent: 'Password must be set',
      passwordMustBeTheSame: 'Passwords must be the same',
      loginMinLength: 'Login must be at least 3 characters long',
      passwordComplexity: 'Invalid password. Please meet the requirements: 8 characters minimum, 1 digit, 1 letter, 1 special character',
    },
    button: 'Register',
    successMessage: 'You have successfully registered'
  },
  linkPanel: {
    noUrl: 'URL not exists',
    urlCopied: 'URL is copied to clipboard',
    invite: 'Invite to group:',
  },
  regForm: {
    buttons: {
      submit: 'Participate',
    },
    thanksMessage: 'Thank you for your participation!',
  },
  buttons: {
    add: 'Add',
    modify: 'Modify',
    delete: 'Delete',
    cancel: 'Cancel',
    save: 'Save',
    ok: 'Ok',
    login: 'Login',
    logout: 'Logout',
    backButton: 'Back',
  },
  recordsPerPage: 'Records per page',
  exception: {
    tooManyLoginAttempts: 'Too many login attempts. Try again in 1 minute',
    invalidCredentials: 'Invalid username or password',
    emailExists: 'Email already exists',
    usernameExists: 'Username already exists',
    authenticationFailed: 'Authentication failed',
    badCredentials: 'Authentication Failed. Username or Password not valid.'
  },
  footerInfo: {
    powered: 'Powered by Groups',
  },
  deleteConfirmation: "Are you sure you want to delete this item?"
}
