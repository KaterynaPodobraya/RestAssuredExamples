import org.springframework.cloud.contract.spec.Contract

import java.text.SimpleDateFormat

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
String birthDate = sdf.format(new Date())

Contract.make {
	request {
		method 'GET'
		url '/persons/1'
	}
	response { 
		status OK()
		body([
			id: 1,
			firstName: 'Piotr',
			lastName: 'Minkowski',
			gender: $(regex('(MALE|FEMALE)')),
			accountNumber: $(regex(positiveInt())),
			//birthDate: $(regex(isoDate())
			birthDate: birthDate

		])
		headers { 
			contentType(applicationJson())
		}
	}
}