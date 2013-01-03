.text
_malloc:
    li $v0,9
    syscall
    j $ra

_printi:
    li $v0, 1
    syscall
    j $ra

_print:
    li $v0, 4
    syscall
    j $ra

_flush:
    j $ra

_strcmp:
    strcmp_test:
    lb $a2 ($a0)
    lb $a3 ($a1)
    beq $a2, $zero, strcmp_end
    beq $a3, $zero, strcmp_end
    bgt $a2, $a3  strcmp_great
    blt $a2, $a3  strcmp_less
    add $a0, $a0, 1
    add $a1, $a1, 1
    j strcmp_test
    strcmp_great:
    li $v0, 1
    j $ra
    strcmp_less:
    li $v0, -1
    j $ra
    strcmp_end:
    bne $a2 $zero strcmp_great
    bne $a3 $zero strcmp_less
    li $v0, 0
    j $ra

_size:
    move $v0, $zero
    size_loop:
    lb $a1 ($a0)
    beq $a1, $zero size_exit
    add $v0, $v0, 1
    add $a0, $a0, 1
    j size_loop
    size_exit:
    j $ra

_ord:
    lb $a1,($a0)
    li $v0,-1
    beqz $a1,Lrunt5
    lb $v0,($a0)
    Lrunt5:
    j $ra

_getchar:
    li $v0, 9 
    li $a0, 2
    syscall
    move $a0, $v0
    li $a1, 2
    li $v0, 8
    syscall
    move $v0, $a0
    j $ra

_chr:
    move $a1, $a0
    li $v0, 9
    li $a0, 2
    syscall
    sb $a1 ($v0)
    sb $zero 1($v0)
    j $ra

_exit:
    li $v0, 10
    syscall

_substring:
    add $a1, $a0, $a1
    move $a3, $a1
    li $v0, 9
    add $a2, $a2, 1
    move $a0, $a2
    add $a0, $a0, 1 
    syscall
    # got a new string in $v0
    add $a2,$a2,$a3
    add $a2,$a2,-1
    move $a0, $v0
    substring_copy:
    beq $a1 $a2 substring_exit
    lb $a3 ($a1)
    sb $a3 ($a0)
    add $a1, $a1, 1
    add $a0, $a0, 1 
    j substring_copy
    substring_exit:
    sb $zero, ($a0)
    j $ra

_not:
    seq $v0,$a0,0
    j $ra

_copy:
    copy_loop:
    lb $a2, ($a1)
    beq $zero, $a2 copy_exit 
    sb $a2, ($a0)   
    add $a0,$a0,1
    add $a1,$a1,1
    j copy_loop
    copy_exit:
    sb $zero, ($a0)
    move $v0, $a0
    j $ra

_concat:
    sw $a0, -4($sp)
    sw $a1, -8($sp)
    sw $ra, -12($sp)
    jal _size
    li $a3, 1
    add $a3,$a3,$v0
    lw $a0, -8($sp)
    jal _size
    add $a3, $a3, $v0
    move $a0, $a3
    li $v0, 9
    syscall 
    move $a3, $v0
    move $a0, $v0
    lw   $a1, -4($sp)
    jal _copy
    move $a0, $v0
    lw $a1, -8($sp)
    jal _copy
    move $v0, $a3
    lw $ra, -12($sp)
    j $ra
#	Tiger Compiler MIPS Runtime
#       (original code by Andrew Appel,
#        partial rewrite, fixes and comments by 
#        Torsten.Grust@uni-konstanz.de)
#	
#	Concatenate this file `runtime.s' with the
#	Tiger-generated MIPS assembly output `*.s'
#	and load the concatenated file into SPIM.
#
#       Example:
#	$ cat runtime.s foo.s > foo-run.s
#	$ xspim -file foo-run.s


# string representation:
#
# "foo" --> .word  3
#           .ascii "foo"		
#           .align 2

# initArray -- allocate an array of given number of entries,
#	       then initialize all array entries to given value
#
# input:  $a0 number of desired array entries
#         $a1 initialization value

	.text

_initArray:
	subu $sp,$sp,4                  # establish frame...
	sw $ra,($sp)                    # ...save $ra

	sll $a0,$a0,2			# compute array size (# bytes)
        jal malloc
	move $a2,$v0			# current array entry
	b Lrunt2
Lrunt1:
	sw $a1,($a2)			# store initializer in current entry
	sub $a0,$a0,4			# count array entries
	add $a2,$a2,4			# next array entry is current
Lrunt2:		
	bgtz $a0, Lrunt1		# array completely initialized?

	lw $ra,($sp)                      
	addiu $sp,$sp,4                 # release frame
	j $ra


# allocRecord -- allocate a block of memory of given size.
#		 then initialize the block to all 0
#
# input:  $a0 block size
# output: $v0 pointer to allocated block

_allocRecord:
	subu $sp,$sp,4                  # establish frame...
	sw $ra,($sp)                    # ...save $ra

	jal malloc
	
	move $a2,$v0			# save pointer into allocated block
	b Lrunt4
Lrunt3:
	sw $0,($a2)			# store 0 (32-bit) in block
	sub $a0,$a0,4			# count words in block
	add $a2,$a2,4			# advance pointer into block
Lrunt4:	
	bgtz $a0, Lrunt3		# 0-ed whole block already?

	lw $ra,($sp)                      
	addiu $sp,$sp,4                 # release frame
	j $ra

# strcmp  -- compare two string, return 1 if > and 0 = and -1 < 
# coded by crow @ 2008-04-19	
# 
# input:  $a0 string1
#         $a1 string2

strcmp:
	lw	$t1,0($a0)		# read the length of string1
	lw	$t2,0($a1)		# read the length of string2
	addiu	$a0,$a0,3		# set the pointer before the first character
	addiu	$a1,$a1,3		# set the pointer before the first character
_cmpEntry:	
	beq	$t1,$zero,_strChkEQ	# length of s1 = 0
	addiu	$a0,$a0,1		# set the pointer to the next character
	addiu	$a1,$a1,1
	beq	$t2,$zero,_strGT	# length of s2 = 0 and length of s1 != 0
	lb	$t3,0($a0)		# load c1
	lb	$t4,0($a1)		# load c2
	addiu	$t1,$t1,-1		# len1 = len1 - 1	
	addiu	$t2,$t2,-1		# len2 = len2 - 1
	beq	$t3,$t4,_cmpEntry	# if c2 = c1 goto _cmpEntry

	nop				# delay slot,if necessary

	bgt	$t3,$t4,_strGT		# if c1 < c2 then goto _strLT else goto str_GT				
_strLT:					# s1 < s2
	li	$v0,-1			
	jr	$ra
_strChkEQ:
	bne	$t2,$zero,_strLT	# check if length of s1 = 0 and length of s2 != 0	
	li	$v0,0			# len(s1) = 0 and len(s2) = 0 => s1 = s2
	jr	$ra			
_strGT:					# s1 > s2
	li	$v0,1
	jr	$ra


# stringEqual -- equality test for two argument strings,
#                return 1 if strings are equal, 0 otherwise
#
# input:  $a0 pointer to string rep 1st string
#         $a1 pointer to string rep 2nd string
# output: $v0 (0/1)

_stringEqual:
	beq $a0,$a1,Lrunt10		# same string rep => equal
	lw  $a2,($a0)			# access string length 1st string
	lw  $a3,($a1)			# access string length 2nd string
	addiu $a0,$a0,4			# first char of 1st string
	addiu $a1,$a1,4			# first char of 2nd string
	beq $a2,$a3,Lrunt11		
Lrunt13:
	li  $v0,0			# lengths differ => not equal
	j $ra
Lrunt12:
	lbu  $t0,($a0)			# current char 1st string
	lbu  $t1,($a1)			# current char 2nd string
	bne  $t0,$t1,Lrunt13		# chars differ => not equal
	addiu $a0,$a0,1			# advance to next char pair
	addiu $a1,$a1,1
	addiu $a2,$a2,-1		# one char pair compared
Lrunt11:	
	bgez $a2,Lrunt12		# more char pairs to compare?
Lrunt10:
	li $v0,1
	j $ra


# print -- print the string argument on the SPIM console
#
# input:  $a0 points to string rep 
# output: -

	.data

printbuf:
	.space 128			# buffer to print from
	.byte 0
	.align 2

	.text
	
print:
	lw $t0,($a0)			# access string length
	sra $t1,$t0,7			# how many number 128-byte blocks?
	addiu $t2,$a0,4			# current char to copy
	andi $t3,$t0,0x7f		# # chars in 1st block (<= 128)
Lrunt56:
	sb $0,printbuf($t3)		# zero-terminate block
	li $t4,0			# char counter
Lrunt55:
	bge $t4,$t3,Lrunt54		# more chars to copy?
	lbu $a0,($t2)			# copy char from string to buffer
	sb $a0,printbuf($t4)		
	addiu $t4,$t4,1			# one char copied
	addiu $t2,$t2,1			# advance to next char
	b Lrunt55
Lrunt54:
	la $a0,printbuf			# print buffer contents
	li $v0,4			# SPIM print system call
	syscall

	li $t3,128			# next block has 128 bytes
	addi $t1,$t1,-1			# one block completed
	bgez $t1,Lrunt56		# more blocks to print?
	j $ra

# fflush -- flush output stream
#	    (a no-op for SPIM)
#
# input:  -
# output: -

flush:
	j $ra



	.data

# RT_chars -- representation of characters with codes 0..256
#             as 256 strings of length 1
#
# main initializes this with a block of constant strings of the form
# "\x00", "\x01", ..., "a", "b", "c", ..., "\xfe", "\xff"
	
RT_chars: 
	.space 2048			# 256 * (4 + 4)
	
# RT_empty -- representation of the empty string (length = 0)

RT_empty: 
	.word 0				# string length

	.text






# chr -- return string (of length 1) containing the character
#        with given character code
#
# input:  $a0 character code (0..255)
# output: $v0 pointer to string rep containing character

	.data
RT_chrrange: 
	.asciiz "[Tiger Runtime] chr(): character out of range\n"

	.text

chr:
	and $a1,$a0,0xffffff00          # check char code to be
	bnez $a1,Lrunt31		#   in range 0..255
	sll  $a0,$a0,3			# compute string addr from char code
	la   $v0,RT_chars($a0)		# retrieve string pointer
	j $ra
Lrunt31:
	li   $v0,4			# SPIM print system call
	la   $a0,RT_chrrange		# print out-of-range error message
	syscall
	li   $v0,10                     # SPIM exit system call
	syscall



# size -- return length of string argument
#
# input:  $a0 pointer to string rep
# output: $v0 length of string (0..)

size:
	lw $v0,($a0)			# access string length information
	j $ra


# substring -- extract substring (specifcied by start and length) 
#              from given string argument
#
# input:  $a0 pointer to string rep
#         $a1 start of substring
#         $a2 length of substring
# output: $v0 string rep of substring

	.data
Lrunt40:  
	.asciiz "[Tiger Runtime] substring(): substring out of bounds\n"

	.text
substring:
	subu $sp,$sp,4                  # establish frame...
	sw $ra,($sp)                    # ...save $ra
	
	lw $t1,($a0)			# access string length
	bltz $a1,Lrunt41		# start < 0? => bounds check failed
	add $t2,$a1,$a2			# last char of substring
	sgt $t3,$t2,$t1			# last char beyond string end?
	bnez $t3,Lrunt41		#   => bounds check failed
	addi $a0,$a0,4
	add $t1,$a0,$a1			# first char of substring
	bne $a2,1,Lrunt42		# substring of length 1?
	lbu $a0,($t1)			#   if so, return const string

	lw $ra,($sp)			# release frame
	addiu $sp,$sp,4
	b chr				# let `chr' do the work instead
Lrunt42:
	bnez $a2,Lrunt43		# substring of length 0?
	la  $v0,RT_empty		#   if so, return empty string
	b Lrunt45
Lrunt43:
	addi $a0,$a2,4			# space needed for substring
	jal malloc
	sw   $a2,($v0)                  # set length of substring
	addi $t2,$v0,4                  # first char of substring
Lrunt44:
	lbu  $t3,($t1)	                # get char from string
	sb   $t3,($t2)			# copy to substring
	addiu $t1,1			# advance in string
	addiu $t2,1			# advance in substring
	addiu $a2,-1			# one char copied
	bgtz $a2,Lrunt44		# more chars to copy?
Lrunt45:	
	lw $ra,($sp)			# release frame
	addiu $sp,$sp,4
	j $ra
Lrunt41:
	li   $v0,4			# SPIM print system call
	la   $a0,Lrunt40		# print out-of-bounds message
	syscall
	li   $v0,10			# SPIM exit system call
	syscall


# concat -- concatenate two strings
#	    
# input:  $a0 string rep of 1st string
#         $a1 string rep of 2nd string
# output: $v0 string rep of concatenated string

concat:
	subu $sp,$sp,4                  # establish frame...
	sw $ra,($sp)                    # ...save $ra
	
	lw $t0,($a0)			# length 1st string
	lw $t1,($a1)			# length 2nd string
	beqz $t0,Lrunt50		# 1st string empty?
	beqz $t1,Lrunt51		# 2nd string empty?
	addiu  $t2,$a0,4		# start of 1st string
	addiu  $t3,$a1,4		# start of 2nd string
	add  $t4,$t0,$t1		# number of chars in result string
	addiu $a0,$t4,4			# plus space for string length header
	jal malloc
	addiu $t5,$v0,4			# pointer to concatenated chars
	sw $t4,($v0)			# set length of concatenated string
Lrunt52:
	lbu $a0,($t2)			# copy chars from 1st string
	sb  $a0,($t5)			#   to result string
	addiu $t2,1			# advance pointers into 1st string
	addiu $t5,1			#   and result string
	addi $t0,-1			# count chars in 1st string
	bgtz $t0,Lrunt52		# more to copy in 1st string?
Lrunt53:
	lbu $a0,($t3)			# copy chars from 2nd string
	sb  $a0,($t5)			#   to result string
	addiu $t3,1			# advance pointers into 2nd string
	addiu $t5,1			#   and result string
	addi $t1,-1			# count chars in 2nd string
	bgtz $t1,Lrunt53		# more to copy in 2nd string?
	b Lrunt57
Lrunt50:
	move $v0,$a1			# 1st string empty, return 2nd
	b Lrunt57
Lrunt51:  
	move $v0,$a0			# 2nd string empty, return 1st
Lrunt57:	
	lw $ra,($sp)			# release frame
	addiu $sp,$sp,4
	j $ra


# _not -- boolean negation,
#         returns 1 if argument = 0, 0 otherwise

# input:  $a0
# output: $v0 = 0/1 



# getchar -- read character from console, 
#	     returns a string rep (of length 1) containing this character
#
# input:  -
# output: $v0 points to string rep
	
	
	.data	
getchbuf:
	.byte 0
	.space 1023
getchptr: 
	.word getchbuf

	.text
getchar:
	lw $a0,getchptr         	# access current char in char buffer
	lbu $v0,($a0)			# if current char != '\0'
	bnez $v0,Lrunt6			#   return string containing it
	li $v0,8			# SPIM read_string system call
	la $a0,getchbuf			# need to read new chars into buffer
	li $a1,1024
	syscall
	la $a0,getchbuf			# access first char in char buffer
	lbu $v0,($a0)			# if current char != '\0'
	bnez $v0,Lrunt6			#   return string containing it
	la $v0,RT_empty			# no char entered, return
	b Lrunt7			#   empty string
Lrunt6:
	sll $v0,$v0,3			# compute address of string
	la $v0,RT_chars($v0)		#   containing current char
	add $a0,$a0,1			# advance current char pointer
Lrunt7:	
	sw $a0,getchptr			# remember for next getchar call
	j $ra				


# malloc -- allocate more bytes,
#           returs a pointer to the newly allocated area
#
# input:  $a0 amount of bytes required (allocates 4 additional bytes)
# output: $v0 points to allocated block (4-byte aligned)

malloc:
	subu $sp,$sp,4                  # stack frame (to save $a0)
	sw $a0,($sp)
	
	addiu $a0,$a0,4			# prepare alignment
	li $v0,9		        # SPIM sbrk system call
	syscall
	addiu $v0,$v0,4			# ensure 4-byte boundary alignment
	li $v1,0xfffffffc
	and $v0,$v0,$v1

	lw $a0,($sp)
	addiu $sp,$sp,4
	j $ra


