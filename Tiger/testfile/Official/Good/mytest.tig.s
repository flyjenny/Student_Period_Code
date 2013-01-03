.globl main
.text

main:
L117:
sub $t0,$fp,$sp
move $fp,$sp
sub $sp,$sp,76
sw $t0,-68($fp)
sw $ra,-64($fp)
sw $s7,-60($fp)
sw $s6,-56($fp)
sw $s5,-52($fp)
sw $s4,-48($fp)
sw $s3,-44($fp)
sw $s2,-40($fp)
sw $s1,-36($fp)
sw $s0,-32($fp)
li $t0,1000
sw $t0,-4($fp)
li $t0,168
add $t1,$fp,-8
lw $t2,-4($fp)
add $t2,$t2,1
move $a0,$t2
li $a1,1
jal _initArray
move $t2,$v0
sw $t2,0($t1)
add $t1,$fp,-12
add $t2,$t0,1
move $a0,$t2
li $a1,0
jal _initArray
move $t2,$v0
sw $t2,0($t1)
add $t1,$fp,-16
lw $t2,-4($fp)
add $t2,$t2,1
move $a0,$t2
li $a1,0
jal _initArray
move $t2,$v0
sw $t2,0($t1)
add $t1,$fp,-20
add $t2,$t0,1
move $a0,$t2
jal _initArray
move $t2,$v0
move $t3,$t2
add $t4,$fp,-28
add $t5,$t0,1
sw $t5,0($t4)
move $t4,$0
sw $t4,-24($fp)
L1:
lw $t5,-24($fp)
lw $t4,-28($fp)
ble$t5,$t4,L2
L0:
sw $t2,0($t1)
move $s4,$0
move $s3,$0
li $t2,2
lw $t0,-4($fp)
li $t1,2
L53:
ble$t1,$t0,L54
L39:
sub $s5,$s4,1
li $s1,1
L112:
ble$s1,$s5,L113
L55:
la $a0,L114
li $v0,4
syscall
move $a0,$s3
li $v0,1
syscall
la $a0,L115
li $v0,4
syscall
lw $s0,-32($fp)
lw $s1,-36($fp)
lw $s2,-40($fp)
lw $s3,-44($fp)
lw $s4,-48($fp)
lw $s5,-52($fp)
lw $s6,-56($fp)
lw $s7,-60($fp)
lw $ra,-64($fp)
move $sp,$fp
lw $t0,-68($fp)
add $fp,$fp,$t0
j L116
L2:
move $t4,$t3
add $t6,$t0,1
li $t5,-1
move $a0,$t6
move $a1,$t5
jal _initArray
move $t5,$v0
sw $t5,0($t4)
add $t3,$t3,4
lw $t4,-24($fp)
add $t4,$t4,1
sw $t4,-24($fp)
j L1
L54:
li $t3,1
lw $t4,-8($fp)
sll $t5,$t1,2
add $t4,$t4,$t5
lw $t4,0($t4)
sub $t4,$t4,1
beq $t4,$0,L52
L51:
move $t3,$0
L52:
bne $t3,$0,L48
L50:
L44:
li $t3,1
mul $t5,$t1,$t2
lw $t4,-4($fp)
sub $t4,$t5,$t4
ble $t4,$0,L47
L46:
move $t3,$0
L47:
bne $t3,$0,L45
L43:
li $t2,2
add $t1,$t1,1
j L53
L48:
add $s4,$s4,1
lw $t4,-12($fp)
sll $t3,$s4,2
add $t3,$t4,$t3
sw $t1,0($t3)
lw $t3,-16($fp)
sll $t4,$t1,2
add $t3,$t3,$t4
sw $s4,0($t3)
j L50
L45:
lw $t4,-8($fp)
mul $t5,$t1,$t2
move $t3,$0
sll $t5,$t5,2
add $t4,$t4,$t5
sw $t3,0($t4)
add $t2,$t2,1
j L44
L113:
move $s7,$s4
add $s0,$s1,1
L110:
ble$s0,$s7,L111
L56:
add $s1,$s1,1
j L112
L111:
lw $t0,-20($fp)
sll $t1,$s1,2
add $t0,$t0,$t1
lw $t0,0($t0)
sll $t1,$s0,2
add $t0,$t0,$t1
lw $t0,0($t0)
li $t1,-1
beq$t0,$t1,L104
L106:
add $s0,$s0,1
j L110
L104:
lw $t0,-20($fp)
sll $t1,$s1,2
add $t0,$t0,$t1
lw $t1,0($t0)
sll $t0,$s0,2
add $s2,$t1,$t0
move $a0,$s1
sw $a0,-72($fp)
move $s6,$s0
lw $t0,-20($fp)
lw $a1,-72($fp)
sll $t1,$a1,2
add $t0,$t0,$t1
lw $t0,0($t0)
sll $t1,$s6,2
add $t0,$t0,$t1
lw $t0,0($t0)
li $t1,-1
beq$t0,$t1,L84
L86:
lw $t0,-20($fp)
lw $a1,-72($fp)
sll $t1,$a1,2
add $t0,$t0,$t1
lw $t0,0($t0)
sll $t1,$s6,2
add $t0,$t0,$t1
lw $t0,0($t0)
li $t1,-1
beq$t0,$t1,L81
L83:
lw $t1,-20($fp)
lw $a1,-72($fp)
sll $t0,$a1,2
add $t0,$t1,$t0
lw $t0,0($t0)
sll $t1,$s6,2
add $t0,$t0,$t1
lw $t0,0($t0)
sw $t0,0($s2)
lw $t1,-20($fp)
sll $t0,$s1,2
add $t0,$t1,$t0
lw $t0,0($t0)
sll $t1,$s0,2
add $t0,$t0,$t1
lw $t0,0($t0)
bgt $t0,1,L107
L109:
j L106
L84:
lw $t1,-12($fp)
sll $t0,$s6,2
add $t0,$t1,$t0
lw $t0,0($t0)
sll $t0,$t0,1
lw $t2,-12($fp)
lw $a1,-72($fp)
sll $t1,$a1,2
add $t1,$t2,$t1
lw $t1,0($t1)
sub $t0,$t0,$t1
lw $t1,-4($fp)
ble$t0,$t1,L87
L89:
j L86
L87:
lw $t0,-8($fp)
lw $t2,-12($fp)
sll $t1,$s6,2
add $t1,$t2,$t1
lw $t1,0($t1)
sll $t1,$t1,1
lw $t2,-12($fp)
lw $a1,-72($fp)
sll $t3,$a1,2
add $t2,$t2,$t3
lw $t2,0($t2)
sub $t1,$t1,$t2
sll $t1,$t1,2
add $t0,$t0,$t1
lw $t0,0($t0)
bne $t0,$0,L90
L92:
j L89
L90:
lw $t1,-20($fp)
lw $a1,-72($fp)
sll $t0,$a1,2
add $t0,$t1,$t0
lw $t1,0($t0)
sll $t0,$s6,2
add $t0,$t1,$t0
move $a0,$t0
sw $a0,-76($fp)
move $t2,$fp
move $t1,$s6
lw $t0,-16($fp)
lw $t3,-12($fp)
sll $t4,$s6,2
add $t3,$t3,$t4
lw $t3,0($t3)
sll $t3,$t3,1
lw $t5,-12($fp)
lw $a1,-72($fp)
sll $t4,$a1,2
add $t4,$t5,$t4
lw $t4,0($t4)
sub $t3,$t3,$t4
move $a0,$t2
move $a1,$t1
sll $t1,$t3,2
add $t0,$t0,$t1
lw $t0,0($t0)
move $a2,$t0
jal getResult
move $t0,$v0
add $t0,$t0,1
lw $a1,-76($fp)
sw $t0,0($a1)
j L92
L81:
li $t0,1
lw $t1,-20($fp)
lw $a1,-72($fp)
sll $t2,$a1,2
add $t1,$t1,$t2
lw $t2,0($t1)
sll $t1,$s6,2
add $t1,$t2,$t1
sw $t0,0($t1)
j L83
L107:
lw $t0,-12($fp)
sll $t1,$s1,2
add $t0,$t0,$t1
lw $t0,0($t0)
lw $t2,-12($fp)
sll $t1,$s0,2
add $t1,$t2,$t1
lw $t1,0($t1)
lw $t3,-20($fp)
sll $t2,$s1,2
add $t2,$t3,$t2
lw $t3,0($t2)
sll $t2,$s0,2
add $t2,$t3,$t2
lw $t2,0($t2)
move $a0,$t0
li $v0,1
syscall
L95:
bgt $t2,$0,L97
L93:
la $a0,L96
li $v0,4
syscall
add $s3,$s3,1
j L109
L97:
la $a0,L94
li $v0,4
syscall
move $a0,$t1
li $v0,1
syscall
sll $t1,$t1,1
sub $t1,$t1,$t0
add $t0,$t0,$t1
div $t0,$t0,2
sub $t2,$t2,1
j L95
L116:

jr $ra
.end main
printF:
L119:
sub $t0,$fp,$sp
move $fp,$sp
sub $sp,$sp,44
sw $t0,-44($fp)
move $t0,$a3
move $t0,$a2
move $t0,$a1
sw $a0,-4($fp)
move $v0,$0
move $sp,$fp
lw $t0,-44($fp)
add $fp,$fp,$t0
j L118
L118:

jr $ra
.end printF
getResult:
L121:
sub $t0,$fp,$sp
move $fp,$sp
sub $sp,$sp,44
sw $t0,-44($fp)
sw $ra,-40($fp)
sw $s4,-24($fp)
sw $s3,-20($fp)
sw $s1,-12($fp)
move $s3,$a2
move $s4,$a1
sw $a0,-4($fp)
lw $t0,-4($fp)
lw $t0,-20($t0)
sll $t1,$s4,2
add $t0,$t0,$t1
lw $t0,0($t0)
sll $t1,$s3,2
add $t0,$t0,$t1
lw $t1,0($t0)
li $t0,-1
beq$t1,$t0,L30
L32:
lw $t0,-4($fp)
lw $t0,-20($t0)
sll $t1,$s4,2
add $t0,$t0,$t1
lw $t1,0($t0)
sll $t0,$s3,2
add $t0,$t1,$t0
lw $t0,0($t0)
li $t1,-1
beq$t0,$t1,L27
L29:
lw $t0,-4($fp)
lw $t0,-20($t0)
sll $t1,$s4,2
add $t0,$t0,$t1
lw $t0,0($t0)
sll $t1,$s3,2
add $t0,$t0,$t1
lw $t0,0($t0)
move $v0,$t0
lw $s1,-12($fp)
lw $s3,-20($fp)
lw $s4,-24($fp)
lw $ra,-40($fp)
move $sp,$fp
lw $t0,-44($fp)
add $fp,$fp,$t0
j L120
L30:
lw $t0,-4($fp)
lw $t1,-12($t0)
sll $t0,$s3,2
add $t0,$t1,$t0
lw $t0,0($t0)
sll $t0,$t0,1
lw $t1,-4($fp)
lw $t2,-12($t1)
sll $t1,$s4,2
add $t1,$t2,$t1
lw $t1,0($t1)
sub $t0,$t0,$t1
lw $t1,-4($fp)
lw $t1,-4($t1)
ble$t0,$t1,L33
L35:
j L32
L33:
lw $t0,-4($fp)
lw $t0,-8($t0)
lw $t1,-4($fp)
lw $t1,-12($t1)
sll $t2,$s3,2
add $t1,$t1,$t2
lw $t1,0($t1)
sll $t1,$t1,1
lw $t2,-4($fp)
lw $t3,-12($t2)
sll $t2,$s4,2
add $t2,$t3,$t2
lw $t2,0($t2)
sub $t1,$t1,$t2
sll $t1,$t1,2
add $t0,$t0,$t1
lw $t0,0($t0)
bne $t0,$0,L36
L38:
j L35
L36:
lw $t0,-4($fp)
lw $t1,-20($t0)
sll $t0,$s4,2
add $t0,$t1,$t0
lw $t0,0($t0)
sll $t1,$s3,2
add $s1,$t0,$t1
lw $t2,-4($fp)
move $t1,$s3
lw $t0,-4($fp)
lw $t0,-16($t0)
lw $t3,-4($fp)
lw $t4,-12($t3)
sll $t3,$s3,2
add $t3,$t4,$t3
lw $t3,0($t3)
sll $t3,$t3,1
lw $t4,-4($fp)
lw $t5,-12($t4)
sll $t4,$s4,2
add $t4,$t5,$t4
lw $t4,0($t4)
sub $t3,$t3,$t4
move $a0,$t2
move $a1,$t1
sll $t1,$t3,2
add $t0,$t0,$t1
lw $t0,0($t0)
move $a2,$t0
jal getResult
move $t0,$v0
add $t0,$t0,1
sw $t0,0($s1)
j L38
L27:
li $t0,1
lw $t1,-4($fp)
lw $t2,-20($t1)
sll $t1,$s4,2
add $t1,$t2,$t1
lw $t1,0($t1)
sll $t2,$s3,2
add $t1,$t1,$t2
sw $t0,0($t1)
j L29
L120:

jr $ra
.end getResult
getPrime:
L123:
sub $t0,$fp,$sp
move $fp,$sp
sub $sp,$sp,44
sw $t0,-44($fp)
sw $a0,-4($fp)
move $v0,$0
move $sp,$fp
lw $t0,-44($fp)
add $fp,$fp,$t0
j L122
L122:

jr $ra
.end getPrime
.data
L115:
    .asciiz "
"
.data
L114:
    .asciiz "Total: "
.data
L96:
    .asciiz "
"
.data
L94:
    .asciiz " "

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

